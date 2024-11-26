package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public ResponseEntity<String> save(@RequestBody @Valid ProfileDto profile) {
        profileService.save(profile);
        return ResponseEntity.ok("Profile saved");
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.findById(id));

    }
    @GetMapping("")
    public ResponseEntity<List<ProfileDto>> getAllRegistrations() {
        return ResponseEntity.ok(profileService.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody @Valid ProfileDto profile) {
        profileService.update(id,profile);
        return ResponseEntity.ok("Profile updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable Long id) {
        profileService.deleteById(id);
        return ResponseEntity.ok("registration deleted");
    }
}
