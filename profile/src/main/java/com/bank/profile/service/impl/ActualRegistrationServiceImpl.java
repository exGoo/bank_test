package com.bank.profile.service.impl;

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

    @Autowired
    public ActualRegistrationServiceImpl(ActualRegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ActualRegistration registration) {
        repository.save(registration);
    }

    @Override
    public List<ActualRegistration> findAll() {
        return repository.findAll();
    }

    @Override
    public ActualRegistration findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void update(Long id, ActualRegistration registration) {
       ActualRegistration OldActualRegistration = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Actual registration not found"));

        OldActualRegistration.setCountry(registration.getCountry());
        OldActualRegistration.setRegion(registration.getRegion());
        OldActualRegistration.setCity(registration.getCity());
        OldActualRegistration.setDistrict(registration.getDistrict());
        OldActualRegistration.setLocality(registration.getLocality());
        OldActualRegistration.setStreet(registration.getStreet());
        OldActualRegistration.setHouseNumber(registration.getHouseNumber());
        OldActualRegistration.setHouseBlock(registration.getHouseBlock());
        OldActualRegistration.setFlatNumber(registration.getFlatNumber());
        OldActualRegistration.setIndex(registration.getIndex());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
