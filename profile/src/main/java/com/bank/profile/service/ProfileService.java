package com.bank.profile.service;

import com.bank.profile.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto save(ProfileDto profile);

    List<ProfileDto> findAll();

    ProfileDto findById(Long id);

    ProfileDto update(Long id, ProfileDto profile);

    void deleteById(Long id);
}
