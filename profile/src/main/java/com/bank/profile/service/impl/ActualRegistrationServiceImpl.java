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
        ActualRegistration actualRegistration = mapper.toEntity(registration);
        ActualRegistration save = repository.save(actualRegistration);
        log.info("{} сохранен с ID: {}", ENTITY_TYPE, save.getId());
        return mapper.toDto(save);
    }

    @Override
    public List<ActualRegistrationDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        List<ActualRegistrationDto> result = mapper.toDtoList(repository.findAll());
        log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
        return result;
    }

    @Override
    public ActualRegistrationDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        ActualRegistrationDto result = mapper.toDto(repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Actual registration not found with ID: " + id)));
        log.info("{} успешно получена: {}", ENTITY_TYPE, result);
        return result;
    }

    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public ActualRegistrationDto update(Long id, ActualRegistrationDto registration) {
        ActualRegistration OldActualRegistration = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Actual registration not found with ID:" + id));
        log.info("{} с ID: {} найден для обновления", ENTITY_TYPE, id);
        mapper.updateEntityFromDto(OldActualRegistration, registration);
        ActualRegistration result = repository.save(OldActualRegistration);
        log.info("{} с ID: {} успешно обновлена", ENTITY_TYPE, id);
        return mapper.toDto(result);
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления {} с ID: {}", ENTITY_TYPE, id);
        repository.deleteById(id);
        log.info("{} с ID: {} удален", ENTITY_TYPE, id);
    }
}
