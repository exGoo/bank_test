package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.NotFoundAuditException;
import com.bank.antifraud.service.implementation.AuditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("integration AuditService tests")
class AuditServiceImplTest {


    @Autowired
    private AuditServiceImpl service;

    private Audit audit;


    @BeforeEach
    void setUp() {
        audit = Audit.builder()
                .entityType("SuspiciousAccountTransfers")
                .operationType("CREATE")
                .createdBy("admin")
                .createdAt(OffsetDateTime.now())
                .entityJson("{\"id\":1, \"accountNumber\":67890}")
                .build();
        audit.setEntityType("SuspiciousAccountTransfers");
        audit.setOperationType("CREATE");
        audit.setCreatedBy("admin");
        audit.setCreatedAt(OffsetDateTime.now());
        audit.setEntityJson("{\"id\":1, \"accountNumber\":67890}");
    }

    @Test
    void save_ShouldSaveAuditSuccessfully() {
        service.save(audit);

        assertNotNull(service.get(audit.getId()));
    }

    @Test
    void getFirstAudit_ShouldReturnExistingAudit() {
        service.save(audit);

        Audit foundAudit = service.getFirstAudit("SuspiciousAccountTransfers",
                "CREATE", 1L);

        assertNotNull(foundAudit);
        assertEquals("SuspiciousAccountTransfers", foundAudit.getEntityType());
        assertEquals("CREATE", foundAudit.getOperationType());
        assertTrue(foundAudit.getEntityJson().contains("\"id\":1"));
    }

    @Test
    void getFirstAudit_ShouldThrowException_WhenAuditNotFound() {
        assertThrows(NotFoundAuditException.class,
                () -> service.getFirstAudit("NonExistent", "CREATE", 999L));
    }


}
