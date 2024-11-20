package com.bank.profile.service.impl;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.dto.mapper.RegistrationMapper;
import com.bank.profile.entity.Registration;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    RegistrationRepository repository;
    RegistrationMapper mapper;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository repository, RegistrationMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public void save(RegistrationDto registration) {

        repository.save(mapper.toEntity(registration));
    }

    @Override
    public List<RegistrationDto> findAll() {

        return mapper.toListDto(repository.findAll());
    }

    @Override
    public RegistrationDto findById(Long id) {

        return mapper.toDto(repository.findById(id).get());
    }

    @Override
    public void update(Long id, RegistrationDto registration) {
        Registration oldRegistration = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("registration not found"));
        mapper.updateEntityFromDto(oldRegistration, registration);
        repository.save(oldRegistration);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
