package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.service.ActualRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ActualRegistrationDto getActualRegistration(@PathVariable Long id) {
       return actualRegistrationService.findById(id);
    }
    @GetMapping
    public List<ActualRegistrationDto> getActualRegistrations() {
        return actualRegistrationService.findAll();
    }
    @PostMapping("")
    public void saveActualRegistration(@RequestBody @Valid ActualRegistrationDto actualRegistration) {
        actualRegistrationService.save(actualRegistration);
    }
    @DeleteMapping("/{id}")
    public void deleteActualRegistration(@PathVariable Long id) {
        actualRegistrationService.deleteById(id);
    }
    @PutMapping("/{id}")
    public void updateActualRegistration(@PathVariable Long id, @RequestBody @Valid ActualRegistrationDto actualRegistration) {
        actualRegistrationService.update(id, actualRegistration);
    }
}
