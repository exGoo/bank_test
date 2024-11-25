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
    RegistrationRepository repository;
    RegistrationMapper mapper;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository repository, RegistrationMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @AuditSave(entityType = "registration")
    public RegistrationDto save(RegistrationDto registration) {
        log.info("попытка сохранить registration : {}", registration);
        try {
            Registration result = repository.save(mapper.toEntity(registration));
            log.info("registration сохранен с ID: {}", result.getId());
            return mapper.toDto(result);
        } catch (Exception e) {
            log.error("ошибка при сохранении registration: {}",e.getMessage());
            throw e;
        }
    }

    @Override
    public List<RegistrationDto> findAll() {
        log.info("Попытка получить список registration");
        try {
            List<Registration> result = repository.findAll();
            log.info("Найдено {} записей registration", result.size());
            return mapper.toListDto(result);
        } catch (Exception e) {
            log.error("Ошибка при получении списка registration записей: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public RegistrationDto findById(Long id) {
        log.info("Попытка получить registration с ID: {}", id);
        try {
            RegistrationDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("registration not found with ID: " + id)));
            log.info("registration успешно получена: {}", result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("registration с ID: {} не найден:{}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = "registration")
    public RegistrationDto update(Long id, RegistrationDto registration) {
        log.info("Попытка обновить registration с ID:{}", id);
        try {
            Registration oldRegistration = repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("registration not found with ID: " + id));
            mapper.updateEntityFromDto(oldRegistration, registration);
            Registration result = repository.save(oldRegistration);
            log.info("registration с ID: {} успешно обновлен", id);
            return mapper.toDto(result);
        } catch (EntityNotFoundException e) {
            log.error("ошибка при обновлении registration с ID: {} message: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Попытка удалить registration с ID:{}", id);
        try {
            repository.deleteById(id);
            log.info("registration с ID: {} удален", id);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при удалении registration: {}", e.getMessage());
            throw e;
        }
    }
}
