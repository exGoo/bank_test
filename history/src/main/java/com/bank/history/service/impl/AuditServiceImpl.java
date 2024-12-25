package com.bank.history.service.impl;

import com.bank.history.dto.AuditDto;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.mapper.AuditMapper;
import com.bank.history.model.Audit;
import com.bank.history.repository.AuditRepository;
import com.bank.history.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuditServiceImpl implements AuditService {
    private final AuditRepository repository;
    private final AuditMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AuditDto> getAllAudits(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::auditToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AuditDto getByCreatedBy(String createdBy) {
        List<Audit> list = repository.findByCreatedBy(createdBy);
        if (list.isEmpty()) {
            throw new HistoryNotFoundException();
        }
        return mapper.auditToDto(list.get(0));
    }

    @Override
    @Transactional
    public void createAudit(AuditDto auditDto) {
        Objects.requireNonNull(auditDto, "Значение не должно быть пустым");
        repository.save(mapper.dtoToAudit(auditDto));
    }
}
