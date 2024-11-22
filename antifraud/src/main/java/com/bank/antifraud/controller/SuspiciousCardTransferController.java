package com.bank.antifraud.controller;

import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.model.SuspiciousCardTransfer;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/sct")
@RequiredArgsConstructor
public class SuspiciousCardTransferController {

    private final SuspiciousCardTransferService sctService;
    private final SuspiciousCardTransferMapper sctMapper;


    @GetMapping
    public ResponseEntity<List<SuspiciousCardTransfer>> getAll() {
        List<SuspiciousCardTransfer> suspiciousCardTransfers = sctService.getAll();
        return ResponseEntity.ok(suspiciousCardTransfers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousCardTransfer> get(@PathVariable("id") Long id) {
        SuspiciousCardTransfer suspiciousCardTransfer = sctService.get(id);
        return ResponseEntity.ok(suspiciousCardTransfer);
    }

    @GetMapping
    public ResponseEntity<Void> add(@RequestParam("sct") SuspiciousCardTransfer sct) {
        sctService.add(sct);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody Long id, @RequestBody SuspiciousCardTransfer sct) {
        sctService.update(id, sct);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestBody Long id) {
        sctService.remove(id);
        return ResponseEntity.noContent().build();
    }

}

