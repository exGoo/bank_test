package com.bank.profile.service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.mapper.PassportMapper;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class PassportServiceImpl implements PassportService {
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
    public void save(PassportDto passport) {
        Passport newPassport = mapper.toEntity(passport);
        Registration registration = registrationRepository.findById(passport.getRegistrationId()).orElseThrow(
                ()->new EntityNotFoundException("Registration not found with ID: " + passport.getRegistrationId()));
        newPassport.setRegistration(registration);
        repository.save(newPassport);
    }

    @Override
    public List<PassportDto> findAll() {

        return mapper.toListDto(repository.findAll());
    }

    @Override
    public PassportDto findById(Long id) {
        return mapper.toDto(repository.findById(id).get());
    }

    @Override
    public void update(Long id, PassportDto passport) {
        Passport oldPassport = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Passport not found"));
        Registration registration = oldPassport.getRegistration();
        if(passport.getRegistrationId() != null) {
            registration = registrationRepository.findById(passport.getRegistrationId()).orElseThrow(
                    ()->new EntityNotFoundException("Registration not found with ID: " + passport.getRegistrationId()));
        }
        mapper.updateEntityFromDto(oldPassport, passport);
        oldPassport.setRegistration(registration);
        repository.save(oldPassport);


    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
