package com.bank.profile.service;

import com.bank.profile.entity.AccountDetails;

import java.util.List;

public interface AccountDetailsService {

    void save(AccountDetails accountDetails);
    List<AccountDetails> findAll();
    AccountDetails findById(Long id);
    void update(Long id,AccountDetails accountDetails);
    void deleteById(Long id);

}
