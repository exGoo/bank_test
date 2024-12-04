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

    ActualRegistrationRepository repository;
    ActualRegistrationMapper mapper;

    @Autowired
    public ActualRegistrationServiceImpl(ActualRegistrationRepository repository, ActualRegistrationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @AuditSave(entityType = "actual_registration")
    public ActualRegistrationDto save(ActualRegistrationDto registration) {
        log.info("попытка сохранить actual_registration: {}", registration);
        try {
            ActualRegistration actualRegistration = mapper.toEntity(registration);
            ActualRegistration save = repository.save(actualRegistration);
            log.info("actual_registration сохранен с ID: {}", save.getId());
            return mapper.toDto(save);
        } catch (Exception e) {
            log.error("Ошибка при создании actual_registration: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ActualRegistrationDto> findAll() {
        log.info("Попытка получить список actual_registration");
        try {
            List<ActualRegistrationDto> result = mapper.toDtoList(repository.findAll());
            log.info("Найдено {} записей actual_registration", result.size());
            return result;
        } catch (Exception e) {
            log.error("Ошибка при получении списка actual_registration записей: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ActualRegistrationDto findById(Long id) {
        log.info("Попытка получить actual_registration с ID: {}", id);
        try {
            ActualRegistrationDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Actual registration not found with ID: " + id)));
            log.info("actual_registration успешно получена: {}", result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("actual_registration с ID: {} не найден:{}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = "actual_registration")
    public ActualRegistrationDto update(Long id, ActualRegistrationDto registration) {
        try {
            ActualRegistration OldActualRegistration = repository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Actual registration not found with ID:" + id));
            log.info("actual_registration с ID: {} найден для обновления", id);
            mapper.updateEntityFromDto(OldActualRegistration, registration);
            ActualRegistration result = repository.save(OldActualRegistration);
            log.info("actual_registration с ID: {} успешно обновлена", id);
            return mapper.toDto(result);

        } catch (EntityNotFoundException e) {
            log.error("actual_registration с ID: {} не найден:{}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления actual_registration с ID: {}", id);
        try {
            repository.deleteById(id);
            log.info("actual_registration с ID: {} удален", id);
        } catch (Exception e) {
            log.error("Ошибка при удалении actual_registration: {}", e.getMessage());
            throw e;
        }
    }
}
