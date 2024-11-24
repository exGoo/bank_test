package com.bank.history.mapper;

import com.bank.history.dto.AuditDto;
import com.bank.history.model.Audit;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuditMapperTest {
    private final AuditMapper mapper = Mappers.getMapper(AuditMapper.class);

    @Test
    void auditToDtoTest() {
        Audit audit = Audit.builder()
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
                .build();

        AuditDto actualResult = mapper.auditToDto(audit);

        assertEquals(audit.getId(), actualResult.getId());
        assertEquals(audit.getEntityType(), actualResult.getEntityType());
        assertEquals(audit.getOperationType(), actualResult.getOperationType());
        assertEquals(audit.getCreatedBy(), actualResult.getCreatedBy());
        assertEquals(audit.getModifiedBy(), actualResult.getModifiedBy());
        assertEquals(audit.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(audit.getModifiedAt(), actualResult.getModifiedAt());
        assertEquals(audit.getEntityJson(), actualResult.getEntityJson());
        assertEquals(audit.getNewEntityJson(), actualResult.getNewEntityJson());
    }

    @Test
    void dtoToAuditTest() {
        AuditDto dto = AuditDto.builder()
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
                .build();

        Audit actualResult = mapper.dtoToAudit(dto);

        assertEquals(dto.getId(), actualResult.getId());
        assertEquals(dto.getEntityType(), actualResult.getEntityType());
        assertEquals(dto.getOperationType(), actualResult.getOperationType());
        assertEquals(dto.getCreatedBy(), actualResult.getCreatedBy());
        assertEquals(dto.getModifiedBy(), actualResult.getModifiedBy());
        assertEquals(dto.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(dto.getModifiedAt(), actualResult.getModifiedAt());
        assertEquals(dto.getEntityJson(), actualResult.getEntityJson());
        assertEquals(dto.getNewEntityJson(), actualResult.getNewEntityJson());
    }

    @Test
    void auditsToDtoListTest() {
        List<Audit> auditList = List.of(
                Audit.builder()
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
                Audit.builder()
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

        List<AuditDto> actualResult = mapper.auditsToDtoList(auditList);

        assertEquals(auditList.size(), actualResult.size());
        assertEquals(auditList.get(0).getId(), actualResult.get(0).getId());
        assertEquals(auditList.get(1).getOperationType(), actualResult.get(1).getOperationType());
    }
}