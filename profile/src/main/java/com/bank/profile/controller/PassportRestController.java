package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.mapper.PassportMapper;
import com.bank.profile.entity.Passport;
import com.bank.profile.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Passports")
public class PassportRestController {


    PassportService passportService;
    PassportMapper mapper;

    @Autowired
    public PassportRestController(PassportService passportService, PassportMapper mapper) {
        this.passportService = passportService;
        this.mapper = mapper;
    }

    @PostMapping("")
    public void save(@RequestBody PassportDto passport) {
        passportService.save(passport);
    }
    @GetMapping("/{id}")
    public PassportDto getRegistrationById(@PathVariable Long id) {
        return passportService.findById(id);
    }
    @GetMapping("")
    public List<PassportDto> getAllRegistrations() {
        return passportService.findAll();
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody PassportDto passport) {
        passportService.update(id,passport);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Long id) {
        passportService.deleteById(id);
    }
}
