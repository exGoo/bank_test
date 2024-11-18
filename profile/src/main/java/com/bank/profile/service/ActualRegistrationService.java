package com.bank.profile.service;

import com.bank.profile.entity.ActualRegistration;

import java.util.List;

public interface ActualRegistrationService {

    void save(ActualRegistration registration);
    List<ActualRegistration> findAll();
    ActualRegistration findById(Long id);
    void update(Long id,ActualRegistration registration);
    void deleteById(Long id);
}
