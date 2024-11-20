package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.entity.AccountDetails;

import java.util.List;

public interface AccountDetailsService {

    void save(AccountDetailsDto accountDetails);
    List<AccountDetailsDto> findAll();
    AccountDetailsDto findById(Long id);
    void update(Long id,AccountDetailsDto accountDetails);
    void deleteById(Long id);

}
