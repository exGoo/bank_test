package com.bank.profile.service.impl;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.dto.mapper.AccountDetailsMapper;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.Profile;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ProfileRepository;
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
    ProfileRepository profileRepository;

    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepository repository, AccountDetailsMapper mapper, ProfileRepository profileRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.profileRepository = profileRepository;
    }

    @Override
    public void save(AccountDetailsDto accountDetails) {
        AccountDetails newDetails = mapper.toEntity(accountDetails);
        Profile profile = profileRepository.findById(accountDetails.getProfileId()).orElseThrow(
                () -> new EntityNotFoundException("Profile not found with ID: " + accountDetails.getProfileId()));
        newDetails.setProfile(profile);
        repository.save(newDetails);
    }

    @Override
    public List<AccountDetailsDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public AccountDetailsDto findById(Long id) {

        return mapper.toDto(repository.findById(id).get());
    }

    @Override
    public void update(Long id, AccountDetailsDto accountDetails) {
        AccountDetails oldDetails = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Account details not found with ID: " + id));
        Profile profile = oldDetails.getProfile();
        if (accountDetails.getProfileId() != null) {
            profile = profileRepository.findById(accountDetails.getProfileId()).orElseThrow(
                    () -> new EntityNotFoundException("Profile not found with ID: " + accountDetails.getProfileId()));
        }
        mapper.updateEntityFromDto(oldDetails, accountDetails);
        oldDetails.setProfile(profile);
        repository.save(oldDetails);

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);

    }
}
