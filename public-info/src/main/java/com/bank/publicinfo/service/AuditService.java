package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.utils.Auditable;

public interface AuditService {

    void saveNewAudit(Auditable<?> entity);

    void refreshAudit(Auditable<?> newEntity);

    Audit findLastAudit(String id, String entityType);
}
