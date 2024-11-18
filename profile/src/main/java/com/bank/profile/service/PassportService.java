package com.bank.profile.service;

import com.bank.profile.entity.Passport;

import java.util.List;

public interface PassportService {
    void save(Passport passport);
    List<Passport> findAll();
    Passport findById(Long id);
    void update(Long id,Passport passport);
    void deleteById(Long id);

}
