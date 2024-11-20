package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.Profile;
import com.bank.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Profiles")
public class ProfileRestController {


    ProfileService profileService;

    @Autowired
    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("")
    public void save(@RequestBody ProfileDto profile) {
        profileService.save(profile);
    }
    @GetMapping("/{id}")
    public ProfileDto getRegistrationById(@PathVariable Long id) {
        return profileService.findById(id);
    }
    @GetMapping("")
    public List<ProfileDto> getAllRegistrations() {
        return profileService.findAll();
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody ProfileDto profile) {
        profileService.update(id,profile);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Long id) {
        profileService.deleteById(id);
    }
}
