package com.bank.publicinfo.service;
import com.bank.publicinfo.dto.ATMDto;


import java.util.List;

public interface ATMService {
    ATMDto findById(Long id);

    List<ATMDto> findAll();

    ATMDto addATM(ATMDto atm);

    void deleteATMById(Long id);

    ATMDto updateATM(Long id, ATMDto atm);

}
