package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
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
@RequestMapping("/Registrations")
public class RegistrationRestController {

    RegistrationService registrationService;

    @Autowired
    public RegistrationRestController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("")
    public ResponseEntity<RegistrationDto> save(@RequestBody @Valid RegistrationDto registration) {
        RegistrationDto result = registrationService.save(registration);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<RegistrationDto>> getAll() {
        return ResponseEntity.ok(registrationService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid RegistrationDto registration) {
        registrationService.update(id, registration);
        return ResponseEntity.ok("Registration Update ");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.ok("Deleted Registration");
    }
}
