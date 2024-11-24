package com.bank.history.service.impl;

import com.bank.history.dto.AuditDto;
import com.bank.history.mapper.AuditMapper;
import com.bank.history.repository.AuditRepository;
import com.bank.history.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository repository;
    private final AuditMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuditDto> getAllAudits() {
        return mapper.auditsToDtoList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AuditDto getByCreatedBy(String createdBy) {
        return mapper.auditToDto(repository.findByCreatedBy(createdBy).get(0));
    }

    @Override
    @Transactional
    public void createAudit(AuditDto auditDto) {
        repository.save(mapper.dtoToAudit(auditDto));
    }
}
