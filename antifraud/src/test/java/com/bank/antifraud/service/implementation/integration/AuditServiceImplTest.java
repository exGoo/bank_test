package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.NotFoundAuditException;
import com.bank.antifraud.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import static com.bank.antifraud.TestsRecourse.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("integration AuditService tests")
class AuditServiceImplTest {

    @Autowired
    private AuditService service;

    private static final Audit AUDIT = Entity.DEFAULT.getAudit();
    private Audit saved;

    @Test
    void save_ShouldSaveAuditSuccessfully() {
        saved = service.save(AUDIT);

        assertNotNull(service.get(saved.getId()));
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void get_ShouldReturnExistingAudit(Long id) {
        if (id == WRONG_ID) {
            assertThrows(NotFoundAuditException.class,
                    () -> service.get(id));
        } else {
            saved = service.save(AUDIT);

            assertNotNull(service.get(saved.getId()));
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void getFirstAudit_ShouldReturnExistingAudit(Long id) {
        if (id == WRONG_ID) {
            assertThrows(NotFoundAuditException.class,
                    () -> service.getFirstAudit(ENTITY_TYPE, OPERATION_TYPE_CREATE, id));
        } else {
            saved = service.save(AUDIT);

            Audit foundAudit = service.getFirstAudit(ENTITY_TYPE, OPERATION_TYPE_CREATE, saved.getId());

            assertNotNull(foundAudit);
            assertEquals(ENTITY_TYPE, foundAudit.getEntityType());
            assertEquals(OPERATION_TYPE_CREATE, foundAudit.getOperationType());
            assertTrue(foundAudit.getEntityJson().contains(MATCHER));
        }
    }
}
