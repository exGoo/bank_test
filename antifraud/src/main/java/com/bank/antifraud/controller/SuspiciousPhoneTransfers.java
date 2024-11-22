package com.bank.antifraud.controller;

import com.bank.antifraud.mapper.SuspiciousPhoneTransfersMapper;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spt")
@RequiredArgsConstructor
public class SuspiciousPhoneTransfers {

    private final SuspiciousPhoneTransfersService sptService;
    private final SuspiciousPhoneTransfersMapper sptMapper;


    @GetMapping
    public ResponseEntity<List<com.bank.antifraud.model.SuspiciousPhoneTransfers>> getAll() {
        List<com.bank.antifraud.model.SuspiciousPhoneTransfers> suspiciousPhoneTransfers = sptService.getAll();
        return ResponseEntity.ok(suspiciousPhoneTransfers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.bank.antifraud.model.SuspiciousPhoneTransfers> get(@PathVariable("id") Long id) {
        com.bank.antifraud.model.SuspiciousPhoneTransfers suspiciousPhoneTransfers = sptService.get(id);
        return ResponseEntity.ok(suspiciousPhoneTransfers);
    }

    @GetMapping
    public ResponseEntity<Void> add(@RequestParam("sat") com.bank.antifraud.model.SuspiciousPhoneTransfers sat) {
        sptService.add(sat);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody Long id, @RequestBody com.bank.antifraud.model.SuspiciousPhoneTransfers sat) {
        sptService.update(id, sat);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestBody Long id) {
        sptService.remove(id);
        return ResponseEntity.noContent().build();
    }

}

