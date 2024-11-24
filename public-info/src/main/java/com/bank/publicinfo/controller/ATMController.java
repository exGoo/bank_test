package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.service.impl.ATMServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atms")
public class ATMController {
    private ATMServiceImpl atmService;
    @Autowired
    public void setAtmService(ATMServiceImpl atmService) {
        this.atmService = atmService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ATMDto> getATMById(@PathVariable Long id) {
        ATMDto atmDto = atmService.findById(id);
        return new ResponseEntity<>(atmDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ATMDto>> getAllAtms() {
        List<ATMDto> atmDto = atmService.findAll();
        return new ResponseEntity<>(atmDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ATMDto> addATM(@RequestBody ATMDto atmDto) {
        ATMDto createdAtm = atmService.addATM(atmDto);
        return new ResponseEntity<>(createdAtm, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ATMDto> updateATM(@PathVariable Long id, @RequestBody ATMDto atmDto) {
        ATMDto updatedATM = atmService.updateATM(id, atmDto);
        return new ResponseEntity<>(updatedATM, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteATM(@PathVariable Long id) {
        atmService.deleteATMById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
