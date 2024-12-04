package com.bank.profile.service.impl;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.mapper.ActualRegistrationMapper;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ActualRegistrationServiceImpl implements ActualRegistrationService {

    private static final String ENTITY_TYPE = "actual_registration";

    ActualRegistrationRepository repository;
    ActualRegistrationMapper mapper;

    @Autowired
    public ActualRegistrationServiceImpl(ActualRegistrationRepository repository, ActualRegistrationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @AuditSave(entityType = ENTITY_TYPE)
    public ActualRegistrationDto save(ActualRegistrationDto registration) {
        log.info("попытка сохранить {}: {}", ENTITY_TYPE, registration);
        try {
            ActualRegistration actualRegistration = mapper.toEntity(registration);
            ActualRegistration save = repository.save(actualRegistration);
            log.info("{} сохранен с ID: {}", ENTITY_TYPE, save.getId());
            return mapper.toDto(save);
        } catch (Exception e) {
            log.error("Ошибка при создании {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ActualRegistrationDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        try {
            List<ActualRegistrationDto> result = mapper.toDtoList(repository.findAll());
            log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
            return result;
        } catch (Exception e) {
            log.error("Ошибка при получении списка {} записей: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public ActualRegistrationDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        try {
            ActualRegistrationDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Actual registration not found with ID: " + id)));
            log.info("{} успешно получена: {}", ENTITY_TYPE, result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("{} с ID: {} не найден:{}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public ActualRegistrationDto update(Long id, ActualRegistrationDto registration) {
        try {
            ActualRegistration OldActualRegistration = repository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Actual registration not found with ID:" + id));
            log.info("{} с ID: {} найден для обновления", ENTITY_TYPE, id);
            mapper.updateEntityFromDto(OldActualRegistration, registration);
            ActualRegistration result = repository.save(OldActualRegistration);
            log.info("{} с ID: {} успешно обновлена", ENTITY_TYPE, id);
            return mapper.toDto(result);

        } catch (EntityNotFoundException e) {
            log.error("{} с ID: {} не найден:{}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления {} с ID: {}", ENTITY_TYPE, id);
        try {
            repository.deleteById(id);
            log.info("{} с ID: {} удален", ENTITY_TYPE, id);
        } catch (Exception e) {
            log.error("Ошибка при удалении {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }
}
