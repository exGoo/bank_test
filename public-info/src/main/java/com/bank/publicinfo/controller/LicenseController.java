package com.bank.publicinfo.controller;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.impl.LicenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licenses")
public class LicenseController {
    private LicenseServiceImpl licenseService;

    @Autowired
    public void setLicenseService(LicenseServiceImpl licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> getLicenseById(@PathVariable Long id) {
        LicenseDto licenseDto = licenseService.findById(id);
        return new ResponseEntity<>(licenseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LicenseDto>> getAllLicenses() {
        List<LicenseDto> licenseDto = licenseService.findAll();
        return new ResponseEntity<>(licenseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LicenseDto> addLicense(@RequestBody LicenseDto licenseDto) {
        LicenseDto createdLicense = licenseService.addLicence(licenseDto);
        return new ResponseEntity<>(createdLicense, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LicenseDto> updateLicense(@PathVariable Long id, @RequestBody LicenseDto licenseDto) {
        LicenseDto updateLicense = licenseService.updateLicense(id, licenseDto);
        return new ResponseEntity<>(updateLicense, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicenceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}