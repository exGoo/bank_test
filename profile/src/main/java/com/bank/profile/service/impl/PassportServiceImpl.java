package com.bank.profile.service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.mapper.PassportMapper;
import com.bank.profile.entity.Passport;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class PassportServiceImpl implements PassportService {
    PassportRepository repository;
    PassportMapper mapper;
    @Autowired
    public PassportServiceImpl(PassportRepository repository, PassportMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(PassportDto passport) {
        repository.save(mapper.toEntity(passport));
    }

    @Override
    public List<PassportDto> findAll() {

        return mapper.toListDto(repository.findAll());
    }

    @Override
    public PassportDto findById(Long id) {
        return mapper.toDto(repository.findById(id).get());
    }

    @Override
    public void update(Long id, PassportDto passport) {
        Passport newPassport = mapper.toEntity(passport);
       Passport OldPassport = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passport not found"));

       OldPassport.setSeries(newPassport.getSeries());
       OldPassport.setNumber(newPassport.getNumber());
       OldPassport.setLastName(newPassport.getLastName());
       OldPassport.setFirstName(newPassport.getFirstName());
       OldPassport.setMiddleName(newPassport.getMiddleName());
       OldPassport.setGender(newPassport.getGender());
       OldPassport.setBirthDate(newPassport.getBirthDate());
       OldPassport.setBirthPlace(newPassport.getBirthPlace());
       OldPassport.setIssuedBy(newPassport.getIssuedBy());
       OldPassport.setDateOfIssue(newPassport.getDateOfIssue());
       OldPassport.setDivisionCode(newPassport.getDivisionCode());
       OldPassport.setExpirationDate(newPassport.getExpirationDate());
       OldPassport.setRegistration(newPassport.getRegistration());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
