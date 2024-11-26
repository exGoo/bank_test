package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.service.ActualRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ActualRegistrationDto> getActualRegistration(@PathVariable Long id) {
       return ResponseEntity.ok(actualRegistrationService.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<ActualRegistrationDto>> getActualRegistrations() {
        return ResponseEntity.ok(actualRegistrationService.findAll());
    }
    @PostMapping("")
    public ResponseEntity<String> saveActualRegistration(@RequestBody @Valid ActualRegistrationDto actualRegistration) {
        actualRegistrationService.save(actualRegistration);
        return ResponseEntity.ok("Actual Registration Saved");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActualRegistration(@PathVariable Long id) {
        actualRegistrationService.deleteById(id);
        return ResponseEntity.ok("Actual Registration Deleted");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateActualRegistration(@PathVariable Long id, @RequestBody @Valid ActualRegistrationDto actualRegistration) {
        actualRegistrationService.update(id, actualRegistration);
        return ResponseEntity.ok("Actual Registration Updated");
    }
}
