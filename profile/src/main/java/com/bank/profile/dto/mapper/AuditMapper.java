package com.bank.profile.dto.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuditMapper {

    Audit toEntity(AuditDto dto);

    AuditDto toDto(Audit audit);

    List<AuditDto> toListDto(List<Audit> audits);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Audit audit, AuditDto dto);
}
