package com.bank.profile.service.impl;

import com.bank.profile.entity.Passport;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PassportServiceImpl implements PassportService {
    PassportRepository repository;

    @Autowired
    public PassportServiceImpl(PassportRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Passport passport) {
        repository.save(passport);
    }

    @Override
    public List<Passport> findAll() {

        return repository.findAll();
    }

    @Override
    public Passport findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void update(Long id, Passport passport) {
       Passport OldPassport = repository.findById(id).orElseThrow(() -> new RuntimeException("Passport not found"));
       OldPassport.setSeries(passport.getSeries());
       OldPassport.setNumber(passport.getNumber());
       OldPassport.setLastName(passport.getLastName());
       OldPassport.setFirstName(passport.getFirstName());
       OldPassport.setMiddleName(passport.getMiddleName());
       OldPassport.setGender(passport.getGender());
       OldPassport.setBirthDate(passport.getBirthDate());
       OldPassport.setBirthPlace(passport.getBirthPlace());
       OldPassport.setIssuedBy(passport.getIssuedBy());
       OldPassport.setDateOfIssue(passport.getDateOfIssue());
       OldPassport.setDivisionCode(passport.getDivisionCode());
       OldPassport.setExpirationDate(passport.getExpirationDate());
       OldPassport.setRegistration(passport.getRegistration());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
