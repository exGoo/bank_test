package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.mapper.ATMMapper;
import com.bank.publicinfo.repository.ATMRepository;
import com.bank.publicinfo.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ATMServiceImpl implements ATMService {
    private ATMRepository atmRepository;
    private ATMMapper atmMapper;

    @Autowired
    public void setAtmRepository(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Autowired
    public void setAtmMapper(ATMMapper atmMapper) {
        this.atmMapper = atmMapper;
    }


    @Override
    public ATMDto findById(Long id) {
        return null;
    }

    @Override
    public List<ATMDto> findAll() {
        return List.of();
    }

    @Override
    public ATMDto addATM(ATMDto atm) {
        return null;
    }

    @Override
    public void deleteATMById(Long id) {

    }

    @Override
    public ATMDto updateATM(Long id, ATMDto atm) {
        return null;
    }

}
