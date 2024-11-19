package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.mapper.AuditMapper;
import com.bank.antifraud.model.Audit;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
