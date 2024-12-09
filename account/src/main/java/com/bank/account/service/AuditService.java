package com.bank.account.service;

import com.bank.account.model.Audit;
import org.springframework.stereotype.Service;

@Service
public interface AuditService {

    void save(Audit audit);

    Audit findLastAuditByUser(String createdBy);

}
