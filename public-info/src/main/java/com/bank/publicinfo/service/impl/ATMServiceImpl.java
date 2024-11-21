package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.repository.ATMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ATMServiceImpl {
    private ATMRepository atmRepository;

    @Autowired
    public void setAtmRepository(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }
}
