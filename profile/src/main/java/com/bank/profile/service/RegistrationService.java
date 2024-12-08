package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDto;

import java.util.List;

public interface RegistrationService {

    RegistrationDto save(RegistrationDto registration);

    List<RegistrationDto> findAll();

    RegistrationDto findById(Long id);

    RegistrationDto update(Long id, RegistrationDto registration);

    void deleteById(Long id);
}
