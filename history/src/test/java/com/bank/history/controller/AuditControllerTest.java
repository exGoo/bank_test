package com.bank.history.controller;

import com.bank.history.dto.AuditDto;
import com.bank.history.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {
    private static final Long HISTORY_ID = 1L;
    private static final Long HISTORY_ID_TWO = 2L;
    private static final String ENTITY_TYPE = "history";
    private static final String OPERATION_TYPE_CREATE = "create";
    private static final String OPERATION_TYPE_UPDATE = "update";
    private static final LocalDateTime CREATED_AT =
            LocalDateTime.of(2024, 12, 12, 12, 12);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.now();
    private static final String CREATE_BY = "1";

    @Mock
    private AuditService service;

    @InjectMocks
    private AuditController controller;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAllAuditsWithPagination() {
        List<AuditDto> auditDtoList = List.of(
                AuditDto.builder()
                        .id(HISTORY_ID)
                        .entityType(ENTITY_TYPE)
                        .operationType(OPERATION_TYPE_CREATE)
                        .createdBy(CREATE_BY)
                        .createdAt(CREATED_AT)
                        .entityJson("{\"id\":1,\"transferAuditId\":0,\"profileAuditId\":0}")
                        .build(),
                AuditDto.builder()
                        .id(HISTORY_ID_TWO)
                        .entityType(ENTITY_TYPE)
                        .operationType(OPERATION_TYPE_UPDATE)
                        .createdBy(CREATE_BY)
                        .modifiedBy(CREATE_BY)
                        .createdAt(CREATED_AT)
                        .modifiedAt(MODIFIED_AT)
                        .entityJson("{\"id\":1,\"transferAuditId\":0,\"profileAuditId\":0}")
                        .newEntityJson("{\"id\":1,\"transferAuditId\":1,\"profileAuditId\":1}")
                        .build()
        );
        Page<AuditDto> auditPage = new PageImpl<>(auditDtoList, pageable, auditDtoList.size());
        when(service.getAllAudits(pageable)).thenReturn(auditPage);

        ResponseEntity<Page<AuditDto>> actualResult = controller.getAllAudits(pageable);

        assertThat(actualResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualResult.getBody()).isEqualTo(auditPage);
        verify(service).getAllAudits(pageable);
    }
}
