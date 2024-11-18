package com.bank.profile.service.impl;

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
    @Autowired
    public RegistrationServiceImpl(RegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Registration registration) {
        repository.save(registration);
    }

    @Override
    public List<Registration> findAll() {

        return repository.findAll();
    }

    @Override
    public Registration findById(Long id) {

        return repository.findById(id).get();
    }

    @Override
    public void update(Long id, Registration registration) {

        Registration oldRegistration = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("registration not found"));
        oldRegistration.setCountry(registration.getCountry());
        oldRegistration.setRegion(registration.getRegion());
        oldRegistration.setCity(registration.getCity());
        oldRegistration.setDistrict(registration.getDistrict());
        oldRegistration.setLocality(registration.getLocality());
        oldRegistration.setStreet(registration.getStreet());
        oldRegistration.setHouseNumber(registration.getHouseNumber());
        oldRegistration.setHouseBlock(registration.getHouseBlock());
        oldRegistration.setFlatNumber(registration.getFlatNumber());
        oldRegistration.setIndex(registration.getIndex());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
