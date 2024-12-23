package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/audit")
public class AuditRestController {
    private final AuditService auditService;
    private final AuditAspect auditAspect;

    @Autowired
    public AuditRestController(AuditService auditService, AuditAspect auditAspect) {
        this.auditService = auditService;
        this.auditAspect = auditAspect;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<AuditDTO>> getAuditById(@PathVariable Long id) {
        return new ResponseEntity<>(auditService.getAuditById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<AuditDTO>> getAuditList() {
        final List<AuditDTO> auditsDTO = auditService.allAudit();

        if (auditsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(auditsDTO, HttpStatus.OK);
    }
}
