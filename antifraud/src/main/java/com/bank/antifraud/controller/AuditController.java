package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.dto.AuditUpdateDto;
import com.bank.antifraud.mapper.AuditMapper;
import com.bank.antifraud.model.Audit;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;
    private final AuditMapper auditMapper;

    @GetMapping
    public ResponseEntity<List<AuditDto>> getAll() {
        List<Audit> auditList = auditService.getAll();
        List<AuditDto> auditDtoList = auditMapper.toDtoList(auditList);
        return ResponseEntity.ok(auditDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditDto> get(@PathVariable Long id) {
        AuditDto auditDto = auditMapper.toDto(auditService.get(id));
        return ResponseEntity.ok(auditDto);
    }

    @PatchMapping()
    public ResponseEntity<AuditDto> update(@RequestBody AuditUpdateDto auditUpdateDto) {
        Audit audit = auditMapper.updateDtoToEntity(auditUpdateDto);
        auditService.update(audit);
        AuditDto auditDto = auditMapper.toDto(auditService.get(audit.getId()));
        return ResponseEntity.ok(auditDto);
    }

}
