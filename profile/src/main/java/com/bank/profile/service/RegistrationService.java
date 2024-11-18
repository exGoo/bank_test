package com.bank.profile.service;

import com.bank.profile.entity.Registration;

import java.util.List;

public interface RegistrationService {
    void save(Registration registration);
    List<Registration> findAll();
    Registration findById(Long id);
    void update(Long id,Registration registration);
    void deleteById(Long id);
}
