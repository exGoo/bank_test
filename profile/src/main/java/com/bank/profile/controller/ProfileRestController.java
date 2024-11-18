package com.bank.profile.controller;

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
    public void save(@RequestBody Profile profile) {
        System.out.println(profile);
        profileService.save(profile);
    }
    @GetMapping("/{id}")
    public Profile getRegistrationById(@PathVariable Long id) {
        return profileService.findById(id);
    }
    @GetMapping("")
    public List<Profile> getAllRegistrations() {
        return profileService.findAll();
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody Profile profile) {
        profileService.update(id,profile);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Long id) {
        profileService.deleteById(id);
    }
}
