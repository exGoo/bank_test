package com.bank.transfer.service;

import com.bank.transfer.dto.AuditDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AuditService {
    Optional<AuditDTO> getAuditById(Long id);

    List<AuditDTO> allAudit();
}
