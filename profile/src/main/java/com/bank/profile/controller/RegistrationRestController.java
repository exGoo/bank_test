package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.Registration;
import com.bank.profile.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Registrations")
public class RegistrationRestController {

    RegistrationService registrationService;

    @Autowired
    public RegistrationRestController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("")
    public void save(@RequestBody RegistrationDto registration) {
        registrationService.save(registration);
    }
    @GetMapping("/{id}")
    public RegistrationDto getRegistrationById(@PathVariable Long id) {
        return registrationService.findById(id);
    }
    @GetMapping("")
    public List<RegistrationDto> getAllRegistrations() {
        return registrationService.findAll();
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody RegistrationDto registration) {
        registrationService.update(id,registration);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Long id) {
        registrationService.deleteById(id);
    }

}
