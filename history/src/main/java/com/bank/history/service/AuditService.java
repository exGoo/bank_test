package com.bank.history.service;

import com.bank.history.dto.AuditDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {

    Page<AuditDto> getAllAudits(Pageable pageable);

    AuditDto getByCreatedBy(String createdBy);

    void createAudit(AuditDto auditDto);
}
