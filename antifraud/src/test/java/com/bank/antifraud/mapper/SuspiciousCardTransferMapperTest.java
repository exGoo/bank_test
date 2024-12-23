package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
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

@DisplayName("SuspiciousCardTransfer mapper tests")
class SuspiciousCardTransferMapperTest {

    private final SuspiciousCardTransferMapper mapper = Mappers.getMapper(SuspiciousCardTransferMapper.class);
    private static final SuspiciousCardTransferDto DTO = Dto.DEFAULT.getSctDto();
    private static final SuspiciousCardTransfer ENTITY = Entity.DEFAULT.getSct();

    @Test
    void toEntity_shouldMapDtoToEntity() {
        SuspiciousCardTransfer mappedEntity = mapper.toEntity(DTO);

        assertNotNull(mappedEntity);
        assertNull(mappedEntity.getId());
        assertEquals(ENTITY.getCardTransferId(), mappedEntity.getCardTransferId());
        assertEquals(ENTITY.getIsBlocked(), mappedEntity.getIsBlocked());
        assertEquals(ENTITY.getIsSuspicious(), mappedEntity.getIsSuspicious());
        assertEquals(ENTITY.getBlockedReason(), mappedEntity.getBlockedReason());
        assertEquals(ENTITY.getSuspiciousReason(), mappedEntity.getSuspiciousReason());
    }

    @Test
    void toDto_shouldMapEntityToDto() {
        SuspiciousCardTransferDto dto = mapper.toDto(ENTITY);

        assertNotNull(dto);
        assertEquals(DTO, dto);
    }

    @Test
    void toDtoList_shouldMapEntityListToDtoList() {
        SuspiciousCardTransfer entity2 = new SuspiciousCardTransfer();
        entity2.setId(2L);

        List<SuspiciousCardTransfer> listEntity = Arrays.asList(ENTITY, entity2);

        List<SuspiciousCardTransferDto> listDto = mapper.toDtoList(listEntity);

        assertNotNull(listDto);
        assertEquals(listEntity.size(), listDto.size());
        assertEquals(ENTITY.getId(), listDto.get(0).getId());
        assertEquals(entity2.getId(), listDto.get(1).getId());
    }

    @Test
    void updateExisting_shouldUpdateEntityFromDto() {
        SuspiciousCardTransfer entityForUpdate = SuspiciousCardTransfer.builder()
                .id(ID)
                .cardTransferId(ID)
                .isBlocked(false)
                .isSuspicious(true)
                .suspiciousReason(SUSPICIOUS_REASON)
                .build();
        SuspiciousCardTransferDto dtoForUpdate = Dto.FOR_UPDATE.getSctDto();
        mapper.updateExisting(entityForUpdate, dtoForUpdate);

        assertEquals(ENTITY.getId(), entityForUpdate.getId());
        assertEquals(ENTITY.getCardTransferId(), entityForUpdate.getCardTransferId());
        assertEquals(dtoForUpdate.getIsBlocked(), entityForUpdate.getIsBlocked());
        assertEquals(ENTITY.getIsSuspicious(), entityForUpdate.getIsSuspicious());
        assertEquals(ENTITY.getSuspiciousReason(), entityForUpdate.getSuspiciousReason());
        assertEquals(dtoForUpdate.getBlockedReason(), entityForUpdate.getBlockedReason());
    }
}
