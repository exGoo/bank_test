package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;
import static com.bank.antifraud.TestsRecourse.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SuspiciousAccountTransfers mapper tests")
class SuspiciousAccountTransfersMapperTest {

    private final SuspiciousAccountTransfersMapper mapper = Mappers.getMapper(SuspiciousAccountTransfersMapper.class);
    private static final SuspiciousAccountTransfersDto DTO = Dto.DEFAULT.getSatDto();
    private static final SuspiciousAccountTransfers ENTITY = Entity.DEFAULT.getSat();

    @Test
    void toEntity_shouldMapDtoToEntity() {
        SuspiciousAccountTransfers mappedEntity = mapper.toEntity(DTO);

        assertNotNull(mappedEntity);
        assertNull(mappedEntity.getId());
        assertEquals(ENTITY.getAccountTransferId(), mappedEntity.getAccountTransferId());
        assertEquals(ENTITY.getIsBlocked(), mappedEntity.getIsBlocked());
        assertEquals(ENTITY.getIsSuspicious(), mappedEntity.getIsSuspicious());
        assertEquals(ENTITY.getBlockedReason(), mappedEntity.getBlockedReason());
        assertEquals(ENTITY.getSuspiciousReason(), mappedEntity.getSuspiciousReason());
    }

    @Test
    void toDto_shouldMapEntityToDto() {
        SuspiciousAccountTransfersDto dto = mapper.toDto(ENTITY);

        assertNotNull(dto);
        assertEquals(DTO, dto);
    }

    @Test
    void toDtoList_shouldMapEntityListToDtoList() {
        SuspiciousAccountTransfers entity2 = new SuspiciousAccountTransfers();
        entity2.setId(2L);

        List<SuspiciousAccountTransfers> listEntity = Arrays.asList(ENTITY, entity2);

        List<SuspiciousAccountTransfersDto> listDto = mapper.toDtoList(listEntity);

        assertNotNull(listDto);
        assertEquals(listEntity.size(), listDto.size());
        assertEquals(ENTITY.getId(), listDto.get(0).getId());
        assertEquals(entity2.getId(), listDto.get(1).getId());
    }

    @Test
    void updateExisting_shouldUpdateEntityFromDto() {
        SuspiciousAccountTransfers entityForUpdate = SuspiciousAccountTransfers.builder()
                .id(ID)
                .accountTransferId(ID)
                .isBlocked(false)
                .isSuspicious(true)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
        SuspiciousAccountTransfersDto dtoForUpdate = Dto.FOR_UPDATE.getSatDto();
        mapper.updateExisting(entityForUpdate, dtoForUpdate);

        assertEquals(ENTITY.getId(), entityForUpdate.getId());
        assertEquals(ENTITY.getAccountTransferId(), entityForUpdate.getAccountTransferId());
        assertEquals(dtoForUpdate.getIsBlocked(), entityForUpdate.getIsBlocked());
        assertEquals(ENTITY.getIsSuspicious(), entityForUpdate.getIsSuspicious());
        assertEquals(ENTITY.getSuspiciousReason(), entityForUpdate.getSuspiciousReason());
        assertEquals(dtoForUpdate.getBlockedReason(), entityForUpdate.getBlockedReason());
    }
}
