package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.impl.BankDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank-details")
public class BankDetailsController {
    private BankDetailsServiceImpl bankDetailsService;

    @Autowired
    public void setBankDetailsService(BankDetailsServiceImpl bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsDto> getBankDetailsById(@PathVariable Long id) {
        BankDetailsDto bankDetailsDto = bankDetailsService.findById(id);
        return new ResponseEntity<>(bankDetailsDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BankDetailsDto>> getAllBankDetails() {
        List<BankDetailsDto> bankDetailsDtos = bankDetailsService.findAllWithRelations();
        return new ResponseEntity<>(bankDetailsDtos, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<BankDetailsDto>> getBankDetailsByCity(@PathVariable String city) {
        List<BankDetailsDto> bankDetailsDtos = bankDetailsService.findByCity(city);
        return new ResponseEntity<>(bankDetailsDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankDetailsDto> addBankDetails(@RequestBody BankDetailsDto bankDetailsDto) {
        BankDetailsDto createdBankDetails = bankDetailsService.addBankDetails(bankDetailsDto);
        return new ResponseEntity<>(createdBankDetails, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankDetailsDto> updateBankDetails(@PathVariable Long id, @RequestBody BankDetailsDto bankDetailsDto) {
        BankDetailsDto updatedBankDetails = bankDetailsService.updateBankDetails(id, bankDetailsDto);
        return new ResponseEntity<>(updatedBankDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
        bankDetailsService.deleteBankDetailsById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
