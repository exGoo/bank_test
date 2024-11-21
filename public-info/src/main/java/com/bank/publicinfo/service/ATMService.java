package com.bank.publicinfo.service;


import com.bank.publicinfo.entity.ATM;

public interface ATMService {

    ATM addATM(ATM atm);

    void deleteBankDetailsById(Long id);

    ATM updateATM(Long id, ATM atm);

}
