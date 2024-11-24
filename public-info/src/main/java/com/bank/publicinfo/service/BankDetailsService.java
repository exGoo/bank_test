package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BankDetailsDto;
import java.util.List;

public interface BankDetailsService {
    BankDetailsDto findById(Long id);

    List<BankDetailsDto> findAllWithRelations();

    List<BankDetailsDto> findByCity(String city);

    BankDetailsDto addBankDetails(BankDetailsDto bankDetails);

    void deleteBankDetailsById(Long id);

    BankDetailsDto updateBankDetails(Long id, BankDetailsDto bankDetailsDto);
}
