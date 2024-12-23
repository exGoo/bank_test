package com.bank.antifraud.service.implementation.unit;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.NotFoundAuditException;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.implementation.AuditServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static com.bank.antifraud.TestsRecourse.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Audit service unit test")
class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditServiceImpl auditService;

    private static final Audit AUDIT = Entity.DEFAULT.getAudit();

    @Test
    void save_ShouldSaveAudit() {
        when(auditRepository.save(any(Audit.class))).thenReturn(AUDIT);

        auditService.save(AUDIT);

        verify(auditRepository).save(AUDIT);
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void getFirstAudit_ShouldReturnAudit_WhenFound(Long id) {
        if (id == WRONG_ID) {
            when(auditRepository.findAuditByEntityTypeAndOperationTypeAndEntityJsonContaining(
                    anyString(), anyString(), anyString()))
                    .thenReturn(Optional.empty());

            assertThrows(NotFoundAuditException.class,
                    () -> auditService.getFirstAudit(ENTITY_TYPE, OPERATION_TYPE_CREATE, id));
        } else {
            when(auditRepository.findAuditByEntityTypeAndOperationTypeAndEntityJsonContaining(
                    anyString(), anyString(), anyString()))
                    .thenReturn(Optional.of(AUDIT));

            Audit result = auditService.getFirstAudit(ENTITY_TYPE, OPERATION_TYPE_CREATE, id);

            assertNotNull(result);
            assertEquals(id, result.getId());
            assertEquals(ENTITY_TYPE, result.getEntityType());
            assertEquals(OPERATION_TYPE_CREATE, result.getOperationType());
            assertEquals(ENTITY_JSON, result.getEntityJson());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void get_ShouldReturnAudit_WhenFound(Long id) {
        if (id == WRONG_ID) {
            when(auditRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(NotFoundAuditException.class, () -> auditService.get(id));
        } else {
            when(auditRepository.findById(anyLong())).thenReturn(Optional.of(AUDIT));

            Audit result = auditService.get(id);

            assertNotNull(result);
            assertEquals(id, result.getId());
        }
    }
}
