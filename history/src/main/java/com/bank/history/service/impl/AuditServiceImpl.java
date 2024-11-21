package com.bank.history.service.impl;

import com.bank.history.model.Audit;
import com.bank.history.repository.AuditRepository;
import com.bank.history.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository repository;

    @Override
    @Transactional
    public void createAudit(Audit audit) {
        repository.save(audit);
    }
}
