package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.Profile;

import java.util.List;

public interface ProfileService {
    void save(ProfileDto profile);
    List<ProfileDto> findAll();
    ProfileDto findById(Long id);
    void update(Long id,ProfileDto profile);
    void deleteById(Long id);
}
