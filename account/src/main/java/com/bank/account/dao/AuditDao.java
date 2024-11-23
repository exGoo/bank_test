package com.bank.account.dao;

import com.bank.account.model.Audit;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditDao {
    void save(Audit audit);
    Audit findLastAuditByUser(String createdBy);
}
