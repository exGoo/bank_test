package com.bank.profile.controller;

import com.bank.profile.entity.Audit;
import com.bank.profile.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("Audits")
public class AuditRestController {

    AuditService auditService;

    @Autowired
    public AuditRestController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping("")
    public void save(@RequestBody Audit audit) {
        System.out.println(audit);
        auditService.save(audit);
    }
    @GetMapping("/{id}")
    public Audit getRegistrationById(@PathVariable Long id) {
        return auditService.findById(id);
    }
    @GetMapping("")
    public List<Audit> getAllRegistrations() {
        return auditService.findAll();
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody Audit audit) {
        auditService.update(id,audit);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrationById(@PathVariable Long id) {
        auditService.deleteById(id);
    }

}
