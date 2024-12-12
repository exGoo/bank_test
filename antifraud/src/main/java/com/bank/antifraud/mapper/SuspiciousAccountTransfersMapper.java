package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuspiciousAccountTransfersMapper {

    @Mapping(target = "id", ignore = true)
    SuspiciousAccountTransfers toEntity(SuspiciousAccountTransfersDto satDto);

    SuspiciousAccountTransfersDto toDto(SuspiciousAccountTransfers sat);

    List<SuspiciousAccountTransfersDto> toDtoList(List<SuspiciousAccountTransfers> satList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountTransferId", ignore = true)
    SuspiciousAccountTransfers update(SuspiciousAccountTransfersDto satDto);
}
