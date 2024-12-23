package com.bank.transfer.mapper;

import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.entity.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

    AuditDTO auditToAuditDTO(Audit audit);

    Audit auditDTOToAudit(AuditDTO auditDTO);

    List<AuditDTO> auditListToDTOList(List<Audit> audits);

}
