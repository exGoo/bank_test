package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.service.ActualRegistrationService;
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
@RequestMapping("/ActualRegistrations")
public class ActualRegistrationRestController {

    ActualRegistrationService actualRegistrationService;
    @Autowired
    public ActualRegistrationRestController(ActualRegistrationService actualRegistrationService) {
        this.actualRegistrationService = actualRegistrationService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ActualRegistrationDto> getById(@PathVariable Long id) {
       return ResponseEntity.ok(actualRegistrationService.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<ActualRegistrationDto>> getAll() {
        return ResponseEntity.ok(actualRegistrationService.findAll());
    }
    @PostMapping("")
    public ResponseEntity<String> save(@RequestBody @Valid ActualRegistrationDto actualRegistration) {
        actualRegistrationService.save(actualRegistration);
        return ResponseEntity.ok("Actual Registration Saved");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        actualRegistrationService.deleteById(id);
        return ResponseEntity.ok("Actual Registration Deleted");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid ActualRegistrationDto actualRegistration) {
        actualRegistrationService.update(id, actualRegistration);
        return ResponseEntity.ok("Actual Registration Updated");
    }
}
