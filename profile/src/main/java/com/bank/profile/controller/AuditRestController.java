package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<String> save(@RequestBody @Valid AuditDto audit) {
        auditService.save(audit);
        return ResponseEntity.ok("Audit saved");
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuditDto> getRegistrationById(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.findById(id));
    }
    @GetMapping("")
    public ResponseEntity<List<AuditDto>> getAllRegistrations() {
        return ResponseEntity.ok(auditService.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody @Valid AuditDto audit) {
        auditService.update(id,audit);
        return ResponseEntity.ok("Audit updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable Long id) {
        auditService.deleteById(id);
        return ResponseEntity.ok("Audit deleted");
    }

}
