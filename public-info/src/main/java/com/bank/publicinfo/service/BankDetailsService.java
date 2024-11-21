package com.bank.publicinfo.service;


import com.bank.publicinfo.entity.BankDetails;

import java.util.List;

public interface BankDetailsService {
    List<BankDetails> findAllWithRelations();

    BankDetails findById(Long id);

    List<BankDetails> findByCity(String city);

    BankDetails addBankDetails(BankDetails bankDetails);

    void deleteBankDetailsById(Long id);

    BankDetails updateBankDetails(Long id, BankDetails bankDetails);
}
