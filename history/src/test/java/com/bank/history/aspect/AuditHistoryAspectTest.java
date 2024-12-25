package com.bank.history.aspect;

import com.bank.history.dto.AuditDto;
import com.bank.history.exception.AuditHistoryAspectException;
import com.bank.history.model.History;
import com.bank.history.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditHistoryAspectTest {
    private static final Long HISTORY_ID = 1L;
    private static final String ENTITY_TYPE = "history";
    private static final String OPERATION_TYPE_CREATE = "create";
    private static final String OPERATION_TYPE_UPDATE = "update";
    private static final String OPERATION_TYPE_EDIT = "edit";
    private static final LocalDateTime CREATED_AT =
            LocalDateTime.of(2024, 12, 12, 12, 12);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.now();

    private static final History HISTORY = History.builder()
            .id(HISTORY_ID)
            .build();

    private static final AuditDto CURRENT_AUDIT_DTO = AuditDto.builder()
            .entityType(ENTITY_TYPE)
            .operationType(OPERATION_TYPE_UPDATE)
            .createdBy(HISTORY_ID.toString())
            .createdAt(CREATED_AT)
            .entityJson("{\"id\":1}")
            .build();

    @Mock
    private AuditService service;

    @InjectMocks
    private AuditHistoryAspect aspect;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void whenCreateMethodAudit() throws JsonProcessingException {
        AuditDto expected = AuditDto.builder()
                .entityType(ENTITY_TYPE)
                .operationType(OPERATION_TYPE_CREATE)
                .createdBy(HISTORY_ID.toString())
                .createdAt(MODIFIED_AT)
                .entityJson(mapper.writeValueAsString(HISTORY))
                .build();

        aspect.createMethodAudit(HISTORY);

        verify(service).createAudit(argThat(auditDto -> {
            assertThat(auditDto.getEntityType()).isEqualTo(expected.getEntityType());
            assertThat(auditDto.getOperationType()).isEqualTo(expected.getOperationType());
            assertThat(auditDto.getCreatedBy()).isEqualTo(expected.getCreatedBy());
            assertThat(auditDto.getCreatedAt()).isNotNull();
            assertThat(auditDto.getEntityJson()).isEqualTo(expected.getEntityJson());
            return true;
        }));
    }

    @Test
    void whenUpdateMethodAudit() throws JsonProcessingException {
        when(service.getByCreatedBy(HISTORY_ID.toString())).thenReturn(CURRENT_AUDIT_DTO);

        AuditDto expected = AuditDto.builder()
                .entityType(ENTITY_TYPE)
                .operationType(OPERATION_TYPE_UPDATE)
                .createdBy(HISTORY_ID.toString())
                .createdAt(CREATED_AT)
                .modifiedBy(HISTORY_ID.toString())
                .modifiedAt(MODIFIED_AT)
                .newEntityJson(mapper.writeValueAsString(HISTORY))
                .entityJson(CURRENT_AUDIT_DTO.getEntityJson())
                .build();

        aspect.updateMethodAudit(HISTORY);

        verify(service).createAudit(argThat(auditDto -> {
            assertThat(auditDto.getEntityType()).isEqualTo(expected.getEntityType());
            assertThat(auditDto.getOperationType()).isEqualTo(expected.getOperationType());
            assertThat(auditDto.getCreatedBy()).isEqualTo(expected.getCreatedBy());
            assertThat(auditDto.getCreatedAt()).isEqualTo(expected.getCreatedAt());
            assertThat(auditDto.getModifiedBy()).isEqualTo(expected.getModifiedBy());
            assertThat(auditDto.getModifiedAt()).isNotNull();
            assertThat(auditDto.getNewEntityJson()).isEqualTo(expected.getNewEntityJson());
            assertThat(auditDto.getEntityJson()).isEqualTo(expected.getEntityJson());
            return true;
        }));
    }

    @Test
    void whenEditMethodAudit() throws JsonProcessingException {
        when(service.getByCreatedBy(HISTORY_ID.toString())).thenReturn(CURRENT_AUDIT_DTO);

        AuditDto expected = AuditDto.builder()
                .entityType(ENTITY_TYPE)
                .operationType(OPERATION_TYPE_EDIT)
                .createdBy(HISTORY_ID.toString())
                .createdAt(CREATED_AT)
                .modifiedBy(HISTORY_ID.toString())
                .modifiedAt(MODIFIED_AT)
                .newEntityJson(mapper.writeValueAsString(HISTORY))
                .entityJson(CURRENT_AUDIT_DTO.getEntityJson())
                .build();

        aspect.editMethodAudit(HISTORY);

        verify(service).createAudit(argThat(auditDto -> {
            assertThat(auditDto.getEntityType()).isEqualTo(expected.getEntityType());
            assertThat(auditDto.getOperationType()).isEqualTo(expected.getOperationType());
            assertThat(auditDto.getCreatedBy()).isEqualTo(expected.getCreatedBy());
            assertThat(auditDto.getCreatedAt()).isEqualTo(expected.getCreatedAt());
            assertThat(auditDto.getModifiedBy()).isEqualTo(expected.getModifiedBy());
            assertThat(auditDto.getModifiedAt()).isNotNull();
            assertThat(auditDto.getNewEntityJson()).isEqualTo(expected.getNewEntityJson());
            assertThat(auditDto.getEntityJson()).isEqualTo(expected.getEntityJson());
            return true;
        }));
    }

    @Test
    void whenUpdateMethodAuditThenHistoryNotFoundException() {
        when(service.getByCreatedBy(HISTORY_ID.toString())).thenReturn(null);

        assertThatThrownBy(() -> aspect.updateMethodAudit(HISTORY))
                .isInstanceOf(AuditHistoryAspectException.class);
    }

    @Test
    void whenEditMethodAuditThenHistoryNotFoundException() {
        when(service.getByCreatedBy(HISTORY_ID.toString())).thenReturn(null);

        assertThatThrownBy(() -> aspect.editMethodAudit(HISTORY))
                .isInstanceOf(AuditHistoryAspectException.class);
    }
}
