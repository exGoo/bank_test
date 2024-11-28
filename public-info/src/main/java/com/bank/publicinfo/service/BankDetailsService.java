package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;

import java.util.List;

public interface BankDetailsService {
    BankDetailsDto findById(Long id);

    List<BankDetailsDto> findAllWithRelations();

    List<BankDetailsDto> findByCity(String city);

    BankDetails addBankDetails(BankDetails bankDetails);

    void deleteBankDetailsById(Long id);

    BankDetailsDto updateBankDetails(Long id, BankDetailsDto bankDetailsDto);
}
