package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuspiciousAccountTransfersMapper {

    SuspiciousAccountTransfers toEntity(SuspiciousAccountTransfersDto satDto);

    SuspiciousAccountTransfersDto toDto(SuspiciousAccountTransfers sat);

    List<SuspiciousAccountTransfersDto> toDtoList(List<SuspiciousAccountTransfers> satList);

    @Mapping(target = "id", ignore = true)
    SuspiciousAccountTransfers save(SuspiciousAccountTransfersDto satDto);

    @Mapping(target = "accountTransferId", ignore = true)
    SuspiciousAccountTransfers update(SuspiciousAccountTransfersDto satDto);

}
