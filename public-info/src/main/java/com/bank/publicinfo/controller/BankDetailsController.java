package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.service.BankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank-details")
public class BankDetailsController {
    private BankDetailsService bankDetailsService;
    private BankDetailsMapper bankDetailsMapper;

    @Autowired
    public void setBankDetailsMapper(BankDetailsMapper bankDetailsMapper) {
        this.bankDetailsMapper = bankDetailsMapper;
    }

    @Autowired
    public void setBankDetailsService(BankDetailsService bankDetailsService) {
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
    public ResponseEntity<BankDetails> addBankDetails(@RequestBody BankDetailsDto bankDetailsDto) {
        BankDetails bankDetails = bankDetailsMapper.toModel(bankDetailsDto);
        BankDetails createdBankDetails = bankDetailsService.addBankDetails(bankDetails);
        return new ResponseEntity<>(createdBankDetails, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
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
