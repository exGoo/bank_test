package com.bank.transfer.aspects;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.repository.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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

    @Test
    void testRunSaveMethods() throws Exception {
        // Данные для теста
        Long testId = 1L;
        AccountTransfer transfer = new AccountTransfer(); // Предположим, что у вас есть этот класс
        transfer.setId(testId); // Установите ID или другие необходимые поля

        // Мокаем объект ObjectMapper для преобразования в JSON
        String jsonString = "{\"id\":1,\"type\":\"AccountTransfer\"}"; // Пример JSON
        when(objectMapper.writeValueAsString(transfer)).thenReturn(jsonString);

        // Вызов метода
        auditAspect.runSaveMethods(transfer);

        // Захват аргумента, передаваемого в auditRepository.save
        ArgumentCaptor<Audit> auditCaptor = ArgumentCaptor.forClass(Audit.class);
        verify(auditRepository).save(auditCaptor.capture());

        // Проверка, что созданный объект Audit соответствует ожиданиям
        Audit capturedAudit = auditCaptor.getValue();

        assertEquals("AccountTransfer", capturedAudit.getEntityType());
        assertEquals(OperationTypes.CREATE.name(), capturedAudit.getOperationType());
        assertEquals(String.valueOf(testId), capturedAudit.getCreatedBy());
        assertEquals(LocalDateTime.now().getMinute(), capturedAudit.getCreatedAt().getMinute()); // Сравнение по минутам, чтобы учесть точное время
        assertEquals(jsonString, capturedAudit.getEntityJson());
    }



}