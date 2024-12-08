package com.bank.profile.dto.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.Audit;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditMapperTest {

    Audit ENTITY = Audit.builder()
            .id(1L)
            .entityType("type")
            .operationType("operation")
            .createdBy("createdBy")
            .modifiedBy("modifiedBy")
            .createdAt(OffsetDateTime.MIN)
            .modifiedAt(OffsetDateTime.MAX)
            .newEntityJson("newEntityJson")
            .entityJson("entityJson")
            .build();
    AuditDto DTO = AuditDto.builder()
            .id(1L)
            .entityType("type")
            .operationType("operation")
            .createdBy("createdBy")
            .modifiedBy("modifiedBy")
            .createdAt(OffsetDateTime.MIN)
            .modifiedAt(OffsetDateTime.MAX)
            .newEntityJson("newEntityJson")
            .entityJson("entityJson")
            .build();

    AuditMapper mapper = new AuditMapperImpl();

    @Test
    void toEntity() {
        Audit result = mapper.toEntity(DTO);
        assertNotNull(result);
        assertEquals(ENTITY, result);
    }

    @Test
    void toDto() {
        AuditDto result = mapper.toDto(ENTITY);
        assertNotNull(result);
        assertEquals(DTO, result);
    }

    @Test
    void toListDto() {
        List<AuditDto> result = mapper.toListDto(List.of(ENTITY));

        assertNotNull(result);
        assertEquals(List.of(DTO), result);
    }

    @Test
    void updateEntityFromDto() {
        Audit result = new Audit();
        mapper.updateEntityFromDto(result, DTO);

        assertNotNull(result);
        assertEquals(ENTITY.getEntityType(), result.getEntityType());
    }
}
