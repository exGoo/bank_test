package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl {
    private AuditRepository auditRepository;

    @Autowired
    public void setAuditRepository(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }
}
