package com.bank.profile.service.impl;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.mapper.ProfileMapper;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Profile;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository repository;
    ProfileMapper mapper;
    PassportRepository passportRepository;
    AccountDetailsRepository accountDetailsRepository;
    ActualRegistrationRepository actualRegistrationRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository repository, ProfileMapper mapper,
                              PassportRepository passportRepository,
                              AccountDetailsRepository accountDetailsRepository,
                              ActualRegistrationRepository actualRegistrationRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.passportRepository = passportRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.actualRegistrationRepository = actualRegistrationRepository;

    }

    @Override
    public Profile save(ProfileDto profile) {
        Profile newprofile = mapper.toEntity(profile);
        Passport passport = passportRepository.findById(profile.getPassportId()).orElseThrow(
                () -> new EntityNotFoundException("Passport not found with ID: " + profile.getPassportId()));
        ActualRegistration actualRegistration = actualRegistrationRepository
                .findById(profile.getActualRegistrationId()).orElseThrow(
                        () -> new EntityNotFoundException("ActualRegistration not found with ID: "
                                + profile.getActualRegistrationId()));
        List<AccountDetails> accountDetails = accountDetailsRepository.findAllById(profile.getAccountDetailsId());
        newprofile.setAccountDetails(accountDetails);
        newprofile.setPassport(passport);
        newprofile.setActualRegistration(actualRegistration);
       return repository.save(newprofile);
    }

    @Override
    public List<ProfileDto> findAll() {
        return mapper.toListDto(repository.findAll());
    }

    @Override
    public ProfileDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("profile not found with ID: " + id)));
    }

    @Override
    public void update(Long id, ProfileDto profile) {
        Profile oldProfile = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("profile not found with ID: " + id));
        Passport passport = oldProfile.getPassport();
        if (profile.getPassportId() != null) {
            passport = passportRepository.findById(profile.getPassportId()).orElseThrow(
                    () -> new EntityNotFoundException("Passport not found with ID: " + profile.getPassportId()));
        }
        ActualRegistration actualRegistration = oldProfile.getActualRegistration();
        if (profile.getActualRegistrationId() != null) {
            actualRegistration = actualRegistrationRepository.findById(profile.getActualRegistrationId()).orElseThrow(
                    () -> new EntityNotFoundException("ActualRegistration not found with ID: " + profile.getActualRegistrationId()));
        }
        List<AccountDetails> accountDetails = oldProfile.getAccountDetails();
        if (profile.getAccountDetailsId() != null) {
            accountDetails = accountDetails = profile.getAccountDetailsId().isEmpty()
                    ? new ArrayList<>()
                    : accountDetailsRepository.findAllById(profile.getAccountDetailsId());
        }

        mapper.updateEntityFromDto(oldProfile, profile);
        oldProfile.setPassport(passport);
        oldProfile.setActualRegistration(actualRegistration);
        oldProfile.setAccountDetails(accountDetails);
        repository.save(oldProfile);

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
