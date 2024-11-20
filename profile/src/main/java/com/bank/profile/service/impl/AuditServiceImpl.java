package com.bank.profile.service.impl;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.mapper.AuditMapper;
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
    AuditMapper mapper;

    @Autowired
    public AuditServiceImpl(AuditRepository repository, AuditMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(AuditDto audit) {
        repository.save(mapper.toEntity(audit));
    }

    @Override
    public List<AuditDto> findAll() {
        return mapper.toListDto(repository.findAll());
    }

    @Override
    public AuditDto findById(Long id) {
        return mapper.toDto(repository.findById(id).get());
    }

    @Override
    public void update(Long id, AuditDto audit) {
        Audit newAudit = mapper.toEntity(audit);
        Audit oldAudit = repository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("audit not found"));
        oldAudit.setEntityType(newAudit.getEntityType());
        oldAudit.setOperationType(newAudit.getOperationType());
        oldAudit.setCreatedBy(newAudit.getCreatedBy());
        oldAudit.setModifiedBy(newAudit.getModifiedBy());
        oldAudit.setCreatedAt(newAudit.getCreatedAt());
        oldAudit.setModifiedAt(newAudit.getModifiedAt());
        oldAudit.setNewEntityJson(newAudit.getNewEntityJson());
        oldAudit.setEntityJson(newAudit.getEntityJson());

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
