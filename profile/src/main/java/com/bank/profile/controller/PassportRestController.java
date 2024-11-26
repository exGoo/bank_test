package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.mapper.PassportMapper;
import com.bank.profile.service.PassportService;
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
    public ResponseEntity<String> save(@RequestBody @Valid PassportDto passport) {
        passportService.save(passport);
        return ResponseEntity.ok("Passport saved");
    }
    @GetMapping("/{id}")
    public ResponseEntity<PassportDto> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(passportService.findById(id));
    }
    @GetMapping("")
    public ResponseEntity<List<PassportDto>> getAllRegistrations() {
        return ResponseEntity.ok(passportService.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody @Valid PassportDto passport) {
        passportService.update(id,passport);
        return ResponseEntity.ok("Passport updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable Long id) {
        passportService.deleteById(id);
        return ResponseEntity.ok("Passport deleted");
    }
}
