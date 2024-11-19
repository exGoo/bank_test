package com.bank.publicinfo.service;

import com.bank.publicinfo.repository.ATMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ATMService {
    private ATMRepository atmRepository;

    @Autowired
    public void setAtmRepository(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }
}
