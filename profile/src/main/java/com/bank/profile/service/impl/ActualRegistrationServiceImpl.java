package com.bank.profile.service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.mapper.ActualRegistrationMapper;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ActualRegistrationServiceImpl implements ActualRegistrationService {

    ActualRegistrationRepository repository;
    ActualRegistrationMapper mapper;

    @Autowired
    public ActualRegistrationServiceImpl(ActualRegistrationRepository repository, ActualRegistrationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(ActualRegistrationDto registration) {
        repository.save(mapper.toEntity(registration));
    }

    @Override
    public List<ActualRegistrationDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ActualRegistrationDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Actual registration not found with ID: "+id)));
    }

    @Override
    public void update(Long id, ActualRegistrationDto registration) {
        ActualRegistration OldActualRegistration = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Actual registration not found with ID:" + id));

        mapper.updateEntityFromDto(OldActualRegistration, registration);
        repository.save(OldActualRegistration);


    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
