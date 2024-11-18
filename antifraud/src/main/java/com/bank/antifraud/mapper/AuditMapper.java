package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.AuditUpdateDto;
import com.bank.antifraud.model.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditMapper {

    AuditDto toDto(Audit audit);

    List<AuditDto> toDtoList(List<Audit> audits);

    @Mapping(target = "modifiedAt", expression = "java(OffsetDateTime.now().toString())")
    Audit updateDtoToEntity(AuditUpdateDto auditUpdateDto);

}
