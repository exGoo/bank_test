package com.bank.profile.service.impl;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.dto.mapper.RegistrationMapper;
import com.bank.profile.entity.Registration;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private static final String ENTITY_TYPE = "registration";

    RegistrationRepository repository;
    RegistrationMapper mapper;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository repository, RegistrationMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @AuditSave(entityType = ENTITY_TYPE)
    public RegistrationDto save(RegistrationDto registration) {
        log.info("попытка сохранить {} : {}", ENTITY_TYPE, registration);
        try {
            Registration result = repository.save(mapper.toEntity(registration));
            log.info("{} сохранен с ID: {}", ENTITY_TYPE, result.getId());
            return mapper.toDto(result);
        } catch (Exception e) {
            log.error("ошибка при сохранении {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<RegistrationDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        try {
            List<Registration> result = repository.findAll();
            log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
            return mapper.toListDto(result);
        } catch (Exception e) {
            log.error("Ошибка при получении списка {} записей: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public RegistrationDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        try {
            RegistrationDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("registration not found with ID: " + id)));
            log.info("{} успешно получена: {}", ENTITY_TYPE, result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("{} с ID: {} не найден:{}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public RegistrationDto update(Long id, RegistrationDto registration) {
        log.info("Попытка обновить {} с ID:{}", ENTITY_TYPE, id);
        try {
            Registration oldRegistration = repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("registration not found with ID: " + id));
            mapper.updateEntityFromDto(oldRegistration, registration);
            Registration result = repository.save(oldRegistration);
            log.info("{} с ID: {} успешно обновлен", ENTITY_TYPE, id);
            return mapper.toDto(result);
        } catch (EntityNotFoundException e) {
            log.error("ошибка при обновлении {} с ID: {} message: {}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Попытка удалить {} с ID:{}", ENTITY_TYPE, id);
        try {
            repository.deleteById(id);
            log.info("{} с ID: {} удален", ENTITY_TYPE, id);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при удалении {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }
}
