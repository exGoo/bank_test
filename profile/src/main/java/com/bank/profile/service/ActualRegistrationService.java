package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;

import java.util.List;

public interface ActualRegistrationService {
    ActualRegistrationDto save(ActualRegistrationDto registration);

    List<ActualRegistrationDto> findAll();

    ActualRegistrationDto findById(Long id);

    ActualRegistrationDto update(Long id, ActualRegistrationDto registration);

    void deleteById(Long id);
}
