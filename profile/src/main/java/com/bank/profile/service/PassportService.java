package com.bank.profile.service;

import com.bank.profile.dto.PassportDto;

import java.util.List;

public interface PassportService {
    PassportDto save(PassportDto passport);

    List<PassportDto> findAll();

    PassportDto findById(Long id);

    PassportDto update(Long id, PassportDto passport);

    void deleteById(Long id);

}
