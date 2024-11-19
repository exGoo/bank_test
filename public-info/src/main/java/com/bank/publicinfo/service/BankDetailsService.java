package com.bank.publicinfo.service;

import com.bank.publicinfo.repository.BankDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankDetailsService {
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    public void setBankDetailsRepository(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }


}
