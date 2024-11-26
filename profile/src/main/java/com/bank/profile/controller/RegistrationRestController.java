package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> save(@RequestBody @Valid RegistrationDto registration) {
        registrationService.save(registration);
        return ResponseEntity.ok("Registration saved");
    }
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.findById(id));
    }
    @GetMapping("")
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.findAll());

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody @Valid RegistrationDto registration) {
        registrationService.update(id,registration);
        return ResponseEntity.ok("Registration Update ");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.ok("Deleted Registration");
    }

}
