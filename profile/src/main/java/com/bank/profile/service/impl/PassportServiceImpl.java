package com.bank.profile.service.impl;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.mapper.PassportMapper;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.PassportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class PassportServiceImpl implements PassportService {

    private static final String ENTITY_TYPE = "passport";

    PassportRepository repository;
    PassportMapper mapper;
    RegistrationRepository registrationRepository;

    @Autowired
    public PassportServiceImpl(PassportRepository repository, PassportMapper mapper, RegistrationRepository registrationRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.registrationRepository = registrationRepository;
    }

    @Override
    @AuditSave(entityType = ENTITY_TYPE)
    public PassportDto save(PassportDto passport) {
        log.info("попытка сохранить {} : {}", ENTITY_TYPE, passport);
        try {
            Passport newPassport = mapper.toEntity(passport);
            Registration registration = registrationRepository.findById(passport.getRegistrationId()).orElseThrow(
                    () -> new EntityNotFoundException("Registration not found with ID: " + passport.getRegistrationId()));
            newPassport.setRegistration(registration);
            Passport result = repository.save(newPassport);
            log.info("{} сохранен с ID: {}", ENTITY_TYPE, result.getId());
            return mapper.toDto(result);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при создании {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PassportDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        try {
            List<Passport> result = repository.findAll();
            log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
            return mapper.toListDto(result);
        } catch (Exception e) {
            log.error("Ошибка при получении списка {} записей: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public PassportDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        try {
            PassportDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("passport not found with ID: " + id)));
            log.info("actual_registration успешно получена: {}", result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("{} с ID: {} не найден:{}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public PassportDto update(Long id, PassportDto passport) {
        log.info("Попытка обновить {} с ID: {}, новые данные: {}", ENTITY_TYPE, id, passport);
        try {
            Passport oldPassport = repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Passport not found with ID: " + id));
            Registration registration = oldPassport.getRegistration();
            if (passport.getRegistrationId() != null) {
                registration = registrationRepository.findById(passport.getRegistrationId()).orElseThrow(
                        () -> new EntityNotFoundException("Registration not found with ID: " + passport.getRegistrationId()));
            }
            mapper.updateEntityFromDto(oldPassport, passport);
            oldPassport.setRegistration(registration);
            Passport result = repository.save(oldPassport);
            log.info("{} с ID: {} успешно обновлен", ENTITY_TYPE, id);
            return mapper.toDto(result);
        } catch (EntityNotFoundException e) {
            log.error("ошибка при обновлении {} с ID: {} message: {}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления {} с ID: {}", ENTITY_TYPE, id);
        try {
            repository.deleteById(id);
            log.info("{} с ID: {} удален", ENTITY_TYPE, id);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при удалении {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }
}
