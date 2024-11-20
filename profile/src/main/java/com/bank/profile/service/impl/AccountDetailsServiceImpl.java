package com.bank.profile.service.impl;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.dto.mapper.AccountDetailsMapper;
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
    AccountDetailsMapper mapper;

    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepository repository, AccountDetailsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(AccountDetailsDto accountDetails) {
        repository.save(mapper.toEntity(accountDetails));
    }

    @Override
    public List<AccountDetailsDto> findAll() {
        return mapper.toDtoList( repository.findAll());
    }

    @Override
    public AccountDetailsDto findById(Long id) {

       return mapper.toDto( repository.findById(id).get());
    }

    @Override
    public void update(Long id, AccountDetailsDto accountDetails) {
      AccountDetails newDetail = mapper.toEntity(accountDetails);
      AccountDetails oldDetails =  repository.findById(id).orElseThrow(()->
              new EntityNotFoundException("Account details not found"));
        oldDetails.setAccountId(newDetail.getAccountId());
        oldDetails.setProfile(newDetail.getProfile());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);

    }
}
