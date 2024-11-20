package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistration;

import java.util.List;

public interface ActualRegistrationService {

    void save(ActualRegistrationDto registration);
    List<ActualRegistrationDto> findAll();
    ActualRegistrationDto findById(Long id);
    void update(Long id,ActualRegistrationDto registration);
    void deleteById(Long id);
}
