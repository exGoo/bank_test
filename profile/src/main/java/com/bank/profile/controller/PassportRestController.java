package com.bank.profile.controller;

import com.bank.profile.entity.Passport;
import com.bank.profile.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Passports")
public class PassportRestController {


    PassportService passportService;

    @Autowired
    public PassportRestController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("")
    public void save(@RequestBody Passport passport) {
        System.out.println(passport);
        passportService.save(passport);
    }
    @GetMapping("/{id}")
    public Passport getRegistrationById(@PathVariable Long id) {
        return passportService.findById(id);
    }
    @GetMapping("")
    public List<Passport> getAllRegistrations() {
        return passportService.findAll();
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody Passport passport) {
        passportService.update(id,passport);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Long id) {
        passportService.deleteById(id);
    }
}
