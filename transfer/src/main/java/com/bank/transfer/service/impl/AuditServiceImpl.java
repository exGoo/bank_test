package com.bank.transfer.service.impl;

import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.mapper.AuditMapper;
import com.bank.transfer.repository.AuditRepository;
import com.bank.transfer.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper mapper;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository, AuditMapper mapper) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuditDTO> getAuditById(Long id) {
        final Optional<Audit> audit = auditRepository.findById(id);
        return audit.map(mapper::auditToAuditDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public List<AuditDTO> allAudit() {
        final List<Audit> auditList = auditRepository.findAll();
        return mapper.auditListToDTOList(auditList);
    }
}
