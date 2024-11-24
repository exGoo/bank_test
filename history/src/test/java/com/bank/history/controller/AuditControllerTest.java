package com.bank.history.controller;

import com.bank.history.dto.AuditDto;
import com.bank.history.service.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.rmi.MarshalException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @Mock
    private AuditService service;

    @InjectMocks
    private AuditController controller;

    @Test
    void getAllAudits() {
        List<AuditDto> auditDtoList = List.of(
                AuditDto.builder()
                        .id(1L)
                        .entityType("history")
                        .operationType("create")
                        .createdBy("1")
                        .modifiedBy(null)
                        .createdAt(LocalDateTime.now().minusDays(1))
                        .modifiedAt(null)
                        .entityJson("{\"id\":1,\"transferAuditId\":0,\"profileAuditId\":0,\"accountAuditId\":0,"
                                + "\"antiFraudAuditId\":0,\"publicBankInfoAuditId\":0,\"authorizationAuditId\":0}")
                        .newEntityJson(null)
                        .build(),
                AuditDto.builder()
                        .id(2L)
                        .entityType("history")
                        .operationType("update")
                        .createdBy("1")
                        .modifiedBy("1")
                        .createdAt(LocalDateTime.now().minusDays(1))
                        .modifiedAt(LocalDateTime.now())
                        .entityJson("{\"id\":1,\"transferAuditId\":0,\"profileAuditId\":0,\"accountAuditId\":0,"
                                + "\"antiFraudAuditId\":0,\"publicBankInfoAuditId\":0,\"authorizationAuditId\":0}")
                        .newEntityJson("{\"id\":1,\"transferAuditId\":1,\"profileAuditId\":1,\"accountAuditId\":1,"
                                + "\"antiFraudAuditId\":1,\"publicBankInfoAuditId\":1,\"authorizationAuditId\":1}")
                        .build());
        when(service.getAllAudits())
                .thenReturn(auditDtoList);

        ResponseEntity<List<AuditDto>> actualResult =
                controller.getAllAudits();

        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(auditDtoList, actualResult.getBody());
        verify(service).getAllAudits();
    }
}