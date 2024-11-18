package com.bank.profile.service.impl;

import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    AuditRepository repository;

    @Autowired
    public AuditServiceImpl(AuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Audit audit) {
        repository.save(audit);
    }

    @Override
    public List<Audit> findAll() {
        return repository.findAll();
    }

    @Override
    public Audit findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void update(Long id, Audit audit) {
       Audit oldAudit = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("audit not found"));
       oldAudit.setEntityType(audit.getEntityType());
       oldAudit.setOperationType(audit.getOperationType());
       oldAudit.setCreatedBy(audit.getCreatedBy());
       oldAudit.setModifiedBy(audit.getModifiedBy());
       oldAudit.setCreatedAt(audit.getCreatedAt());
       oldAudit.setModifiedAt(audit.getModifiedAt());
       oldAudit.setNewEntityJson(audit.getNewEntityJson());
       oldAudit.setEntityJson(audit.getEntityJson());

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
