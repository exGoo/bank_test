package com.bank.profile.service.impl;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.mapper.AuditMapper;
import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AuditServiceImpl implements AuditService {

    private static final String ENTITY_TYPE = "audit";

    AuditRepository repository;
    AuditMapper mapper;

    @Autowired
    public AuditServiceImpl(AuditRepository repository, AuditMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AuditDto save(AuditDto audit) {
        log.info("попытка сохранить {} : {}", ENTITY_TYPE, audit);
        AuditDto result = mapper.
                toDto(repository.save(mapper.toEntity(audit)));
        log.info("{} сохранен с ID: {}", ENTITY_TYPE, result.getId());
        return result;
    }

    @Override
    public List<AuditDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        List<AuditDto> result = mapper.toListDto(repository.findAll());
        log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
        return result;
    }

    @Override
    public AuditDto findById(Long id) {
        log.info("Попытка получить {} с ID:{}", ENTITY_TYPE, id);
        AuditDto result = mapper.toDto(repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Audit not found with ID: " + id)));
        log.info("{} успешно получена: {}", ENTITY_TYPE, result);
        return result;
    }

    @Override
    public void update(Long id, AuditDto audit) {
        log.info("Попытка обновить {} с ID:{}", ENTITY_TYPE, id);
        Audit oldAudit = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("audit not found with ID: " + id));
        mapper.updateEntityFromDto(oldAudit, audit);
        repository.save(oldAudit);
        log.info("{} с ID: {} успешно обновлен", ENTITY_TYPE, id);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Попытка удалить {} с ID:{}", ENTITY_TYPE, id);
        repository.deleteById(id);
        log.info("{} с ID: {} удален", ENTITY_TYPE, id);

    }
}
