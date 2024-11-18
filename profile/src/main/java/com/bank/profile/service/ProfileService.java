package com.bank.profile.service;

import com.bank.profile.entity.Profile;

import java.util.List;

public interface ProfileService {
    void save(Profile profile);
    List<Profile> findAll();
    Profile findById(Long id);
    void update(Long id,Profile profile);
    void deleteById(Long id);
}
