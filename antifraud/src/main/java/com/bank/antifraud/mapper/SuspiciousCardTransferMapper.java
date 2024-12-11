package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.model.SuspiciousCardTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuspiciousCardTransferMapper {

    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransfer toEntity(SuspiciousCardTransferDto sctDto);

    SuspiciousCardTransferDto toDto(SuspiciousCardTransfer sct);

    List<SuspiciousCardTransferDto> toDtoList(List<SuspiciousCardTransfer> sctList);

    @Mapping(target = "cardTransferId", ignore = true)
    SuspiciousCardTransfer update(SuspiciousCardTransferDto sctDto);

}