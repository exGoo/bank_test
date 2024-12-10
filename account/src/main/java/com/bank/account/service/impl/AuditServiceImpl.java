package com.bank.account.service.impl;

import com.bank.account.dao.AuditDao;
import com.bank.account.model.Audit;
import com.bank.account.service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    private final AuditDao auditDao;

    public AuditServiceImpl(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @Override
    public void save(Audit audit) {
        auditDao.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Audit findLastAuditByUser(String createdBy) {
        return auditDao.findLastAuditByUser(createdBy);
    }
}
