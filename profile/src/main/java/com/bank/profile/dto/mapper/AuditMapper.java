package com.bank.profile.dto.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.Audit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    Audit toEntity(AuditDto dto);

    AuditDto toDto(Audit audit);

    List<AuditDto> toListDto(List<Audit> audits);
}
