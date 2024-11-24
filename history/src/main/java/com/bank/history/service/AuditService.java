package com.bank.history.service;

import com.bank.history.dto.AuditDto;

import java.util.List;

public interface AuditService {

    List<AuditDto> getAllAudits();

    AuditDto getByCreatedBy(String createdBy);

    void createAudit(AuditDto auditDto);
}
