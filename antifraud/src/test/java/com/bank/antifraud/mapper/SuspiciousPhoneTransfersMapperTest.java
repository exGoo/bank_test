package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;
import static com.bank.antifraud.TestsRecourse.Dto;
import static com.bank.antifraud.TestsRecourse.Entity;
import static com.bank.antifraud.TestsRecourse.ID;
import static com.bank.antifraud.TestsRecourse.SUSPICIOUS_REASON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("SuspiciousPhoneTransfers mapper tests")
class SuspiciousPhoneTransfersMapperTest {

    private final SuspiciousPhoneTransfersMapper mapper = Mappers.getMapper(SuspiciousPhoneTransfersMapper.class);
    private static final SuspiciousPhoneTransfersDto DTO = Dto.DEFAULT.getSptDto();
    private static final SuspiciousPhoneTransfers ENTITY = Entity.DEFAULT.getSpt();

    @Test
    void toEntity_shouldMapDtoToEntity() {
        SuspiciousPhoneTransfers mappedEntity = mapper.toEntity(DTO);

        assertNotNull(mappedEntity);
        assertNull(mappedEntity.getId());
        assertEquals(ENTITY.getPhoneTransferId(), mappedEntity.getPhoneTransferId());
        assertEquals(ENTITY.getIsBlocked(), mappedEntity.getIsBlocked());
        assertEquals(ENTITY.getIsSuspicious(), mappedEntity.getIsSuspicious());
        assertEquals(ENTITY.getBlockedReason(), mappedEntity.getBlockedReason());
        assertEquals(ENTITY.getSuspiciousReason(), mappedEntity.getSuspiciousReason());
    }

    @Test
    void toDto_shouldMapEntityToDto() {
        SuspiciousPhoneTransfersDto dto = mapper.toDto(ENTITY);

        assertNotNull(dto);
        assertEquals(DTO, dto);
    }

    @Test
    void toDtoList_shouldMapEntityListToDtoList() {
        SuspiciousPhoneTransfers entity2 = new SuspiciousPhoneTransfers();
        entity2.setId(2L);

        List<SuspiciousPhoneTransfers> listEntity = Arrays.asList(ENTITY, entity2);

        List<SuspiciousPhoneTransfersDto> listDto = mapper.toDtoList(listEntity);

        assertNotNull(listDto);
        assertEquals(listEntity.size(), listDto.size());
        assertEquals(ENTITY.getId(), listDto.get(0).getId());
        assertEquals(entity2.getId(), listDto.get(1).getId());
    }

    @Test
    void updateExisting_shouldUpdateEntityFromDto() {
        SuspiciousPhoneTransfers entityForUpdate = SuspiciousPhoneTransfers.builder()
                .id(ID)
                .phoneTransferId(ID)
                .isBlocked(false)
                .isSuspicious(true)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
        SuspiciousPhoneTransfersDto dtoForUpdate = Dto.FOR_UPDATE.getSptDto();
        mapper.updateExisting(entityForUpdate, dtoForUpdate);

        assertEquals(ENTITY.getId(), entityForUpdate.getId());
        assertEquals(ENTITY.getPhoneTransferId(), entityForUpdate.getPhoneTransferId());
        assertEquals(dtoForUpdate.getIsBlocked(), entityForUpdate.getIsBlocked());
        assertEquals(ENTITY.getIsSuspicious(), entityForUpdate.getIsSuspicious());
        assertEquals(ENTITY.getSuspiciousReason(), entityForUpdate.getSuspiciousReason());
        assertEquals(dtoForUpdate.getBlockedReason(), entityForUpdate.getBlockedReason());
    }
}
