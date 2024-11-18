package com.bank.profile.controller;

import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.service.ActualRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ActualRegistration getActualRegistration(@PathVariable Long id) {
       return actualRegistrationService.findById(id);
    }
    @GetMapping
    public List<ActualRegistration> getActualRegistrations() {
        return actualRegistrationService.findAll();
    }
    @PostMapping("")
    public void saveActualRegistration(@RequestBody ActualRegistration actualRegistration) {
        actualRegistrationService.save(actualRegistration);
    }
    @DeleteMapping("/{id}")
    public void deleteActualRegistration(@PathVariable Long id) {
        actualRegistrationService.deleteById(id);
    }
    @PutMapping("/{id}")
    public void updateActualRegistration(@PathVariable Long id, @RequestBody ActualRegistration actualRegistration) {
        actualRegistrationService.update(id, actualRegistration);
    }
}
