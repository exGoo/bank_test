package com.bank.profile.service.impl;

import com.bank.profile.entity.AccountDetails;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class AccountDetailsServiceImpl implements AccountDetailsService {

    AccountDetailsRepository repository;

    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(AccountDetails accountDetails) {
        repository.save(accountDetails);
    }

    @Override
    public List<AccountDetails> findAll() {
        return repository.findAll();
    }

    @Override
    public AccountDetails findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void update(Long id, AccountDetails accountDetails) {
      AccountDetails oldDetails =  repository.findById(id).orElseThrow(()->
              new EntityNotFoundException("Account details not found"));
        oldDetails.setAccountId(accountDetails.getAccountId());
        oldDetails.setProfile(accountDetails.getProfile());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);

    }
}
