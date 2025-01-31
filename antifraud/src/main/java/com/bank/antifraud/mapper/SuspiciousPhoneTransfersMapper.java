package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface SuspiciousPhoneTransfersMapper {

    @Mapping(target = "id", ignore = true)
    SuspiciousPhoneTransfers toEntity(SuspiciousPhoneTransfersDto sptDto);

    SuspiciousPhoneTransfersDto toDto(SuspiciousPhoneTransfers spt);

    List<SuspiciousPhoneTransfersDto> toDtoList(List<SuspiciousPhoneTransfers> sptList);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExisting(@MappingTarget SuspiciousPhoneTransfers entity, SuspiciousPhoneTransfersDto sptDto);
}
