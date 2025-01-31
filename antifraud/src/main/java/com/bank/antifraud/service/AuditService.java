package com.bank.antifraud.service;

import com.bank.antifraud.entity.Audit;

public interface AuditService {

    Audit save(Audit audit);

    Audit getFirstAudit(String entityType, String operationType, Long id);

    Audit get(Long id);
}
