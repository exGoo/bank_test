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

    AuditRepository repository;
    AuditMapper mapper;

    @Autowired
    public AuditServiceImpl(AuditRepository repository, AuditMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(AuditDto audit) {
        log.info("попытка сохранить audit : {}", audit);
       try {
           repository.save(mapper.toEntity(audit));
           log.info("audit сохранен с ID: {}", audit.getId());
       }
       catch (Exception e) {
           log.error("ошибка при сохранении audit: {}",e.getMessage());
           log.error(e.getMessage());
       }
    }

    @Override
    public List<AuditDto> findAll() {
        log.info("Попытка получить список audit");
        try {
            List<AuditDto> result = mapper.toListDto(repository.findAll());
            log.info("Найдено {} записей audit", result.size());
            return result;
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при получении списка audit записей: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AuditDto findById(Long id) {
        log.info("Попытка получить audit с ID:{}", id);
        try {
            AuditDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Audit not found with ID: " + id)));
            log.info("Audit успешно получена: {}", result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("Audit с ID: {} не найден:{}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(Long id, AuditDto audit) {
        log.info("Попытка обновить audit с ID:{}", id);
        try {
            Audit oldAudit = repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("audit not found with ID: " + id));
            mapper.updateEntityFromDto(oldAudit, audit);
            repository.save(oldAudit);
            log.info("audit с ID: {} успешно обновлен", id);
        } catch (EntityNotFoundException e) {
            log.error("ошибка при обновлении audit с ID: {} message: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Попытка удалить audit с ID:{}", id);
        try {
            repository.deleteById(id);
            log.info("audit с ID: {} удален", id);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при удалении audit: {}", e.getMessage());
            throw e;
        }
    }
}
