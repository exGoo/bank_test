package com.bank.antifraud.aop;

import com.bank.antifraud.annotation.Auditable;
import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.util.users.Admin;
import com.bank.antifraud.util.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.bank.antifraud.TestsRecourse.*;
import static com.bank.antifraud.annotation.Auditable.Action;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auditing unit test")
class AuditAspectTest {

    @Mock
    private AuditService auditService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private User user;

    @Mock
    private Admin admin;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Auditable auditable;

    @InjectMocks
    private AuditAspect auditAspect;

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getJoinPointProceedResult")
    void whenCreateAction_thenAuditCreated(Object testResult, Auditable.EntityType entityType) throws Throwable {
        when(joinPoint.proceed()).thenReturn(testResult);
        when(auditable.action()).thenReturn(Action.CREATE);
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(joinPoint.getSignature().getName()).thenReturn("add");
        when(admin.getUsername()).thenReturn(CREATOR);
        when(auditable.entityType()).thenReturn(entityType);
        when(objectMapper.writeValueAsString(any())).thenReturn(ENTITY_JSON);

        auditAspect.auditing(joinPoint, auditable);

        verify(auditService).save(argThat(audit ->
                    audit.getEntityType().equals(entityType.getStringEntityType()) &&
                    audit.getOperationType().equals(Action.CREATE.getStringAction()) &&
                    audit.getCreatedBy().equals(CREATOR) &&
                    audit.getEntityJson().equals(ENTITY_JSON)));
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getJoinPointProceedResult")
    void whenUpdateAction_thenAuditUpdated(Object testResult, Auditable.EntityType entityType) throws Throwable {

        when(joinPoint.proceed()).thenReturn(testResult);
        when(auditable.action()).thenReturn(Action.UPDATE);
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        when(joinPoint.getSignature().getName()).thenReturn("update");
        when(auditable.entityType()).thenReturn(entityType);
        when(user.getUsername()).thenReturn(MODIFIER);
        when(joinPoint.getArgs()).thenReturn(new Object[]{ID});
        when(objectMapper.writeValueAsString(any())).thenReturn(NEW_ENTITY_JSON);

        String entityTypeString = entityType.getStringEntityType();
        when(auditService.getFirstAudit(entityTypeString, Action.CREATE.getStringAction(), ID))
                .thenReturn(Audit.builder()
                        .id(ID)
                        .entityType(entityTypeString)
                        .operationType(OPERATION_TYPE_CREATE)
                        .createdBy(CREATOR)
                        .entityJson(ENTITY_JSON)
                        .build());

        auditAspect.auditing(joinPoint, auditable);

        verify(auditService).save(argThat(audit ->
                    audit.getEntityType().equals(entityType.getStringEntityType()) &&
                    audit.getOperationType().equals(Action.UPDATE.getStringAction()) &&
                    audit.getCreatedBy().equals(CREATOR) &&
                    audit.getModifiedBy().equals(MODIFIER) &&
                    audit.getNewEntityJson().equals(NEW_ENTITY_JSON) &&
                    audit.getEntityJson().equals(ENTITY_JSON)));
    }

    @Test
    void whenJsonProcessingException_thenThrowsRuntimeException() throws Throwable {
        assertThrows(RuntimeException.class, () -> auditAspect.auditing(joinPoint, auditable));
    }
}
