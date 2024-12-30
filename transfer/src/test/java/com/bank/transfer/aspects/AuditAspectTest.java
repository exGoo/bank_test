package com.bank.transfer.aspects;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.bank.transfer.ResourcesForTests.ENTITY_TYPE_A_T;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.OPERATION_TYPE_CREATE;
import static com.bank.transfer.ResourcesForTests.accountTransfer1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuditAspectTest {
    @Mock
    private AuditRepository auditRepository;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuditAspect auditAspect;
    private AccountTransfer accountTransfer;

    @BeforeEach
    void setUp() {
        accountTransfer=accountTransfer1;
    }

    @Test
    void testRunSaveMethods() throws Exception {

        String jsonString = String.format("{\"id\":%d,\"type\":%s}", ID_2, ENTITY_TYPE_A_T);
        when(objectMapper.writeValueAsString(accountTransfer)).thenReturn(jsonString);

        auditAspect.runSaveMethods(accountTransfer);

        ArgumentCaptor<Audit> auditCaptor = ArgumentCaptor.forClass(Audit.class);
        verify(auditRepository).save(auditCaptor.capture());

        Audit capturedAudit = auditCaptor.getValue();

        assertEquals(ENTITY_TYPE_A_T, capturedAudit.getEntityType());
        assertEquals(OPERATION_TYPE_CREATE, capturedAudit.getOperationType());
        assertEquals(String.valueOf(ID_2), capturedAudit.getCreatedBy());
        assertEquals(LocalDateTime.now().getMinute(), capturedAudit.getCreatedAt().getMinute());
        assertEquals(jsonString, capturedAudit.getEntityJson());
    }
}
