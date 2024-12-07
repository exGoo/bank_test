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
    public PassportServiceImpl(PassportRepository repository,
                               PassportMapper mapper,
                               RegistrationRepository registrationRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.registrationRepository = registrationRepository;
    }

    @Override
    @AuditSave(entityType = ENTITY_TYPE)
    public PassportDto save(PassportDto passport) {
        log.info("попытка сохранить {} : {}", ENTITY_TYPE, passport);
        Passport newPassport = mapper.toEntity(passport);
        Registration registration = registrationRepository.findById(passport.getRegistrationId()).orElseThrow(
                () -> new EntityNotFoundException("Registration not found with ID: " + passport.getRegistrationId()));
        newPassport.setRegistration(registration);
        Passport result = repository.save(newPassport);
        log.info("{} сохранен с ID: {}", ENTITY_TYPE, result.getId());
        return mapper.toDto(result);
    }

    @Override
    public List<PassportDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        List<Passport> result = repository.findAll();
        log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
        return mapper.toListDto(result);
    }

    @Override
    public PassportDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        PassportDto result = mapper.toDto(repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("passport not found with ID: " + id)));
        log.info("actual_registration успешно получена: {}", result);
        return result;
    }

    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public PassportDto update(Long id, PassportDto passport) {
        log.info("Попытка обновить {} с ID: {}, новые данные: {}", ENTITY_TYPE, id, passport);
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
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления {} с ID: {}", ENTITY_TYPE, id);
        repository.deleteById(id);
        log.info("{} с ID: {} удален", ENTITY_TYPE, id);
    }
}
