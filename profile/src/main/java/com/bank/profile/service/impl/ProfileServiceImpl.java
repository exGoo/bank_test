package com.bank.profile.service.impl;

import com.bank.profile.entity.Profile;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository repository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Profile profile) {
        repository.save(profile);
    }

    @Override
    public List<Profile> findAll() {
        return repository.findAll();
    }

    @Override
    public Profile findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void update(Long id, Profile profile) {
        Profile oldProfile = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("profile not found"));
        oldProfile.setPhoneNumber(profile.getPhoneNumber());
        oldProfile.setEmail(profile.getEmail());
        oldProfile.setNameOnCard(profile.getNameOnCard());
        oldProfile.setInn(profile.getInn());
        oldProfile.setSnils(profile.getSnils());
        oldProfile.setPassport(profile.getPassport());
        oldProfile.setActualRegistration(profile.getActualRegistration());
        oldProfile.setAccountDetails(profile.getAccountDetails());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
