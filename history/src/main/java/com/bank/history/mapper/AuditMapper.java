package com.bank.history.mapper;

import com.bank.history.dto.AuditDto;
import com.bank.history.model.Audit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    AuditDto auditToDto(Audit audit);

    Audit dtoToAudit(AuditDto auditDto);

    List<AuditDto> auditsToDtoList(List<Audit> audits);
}
