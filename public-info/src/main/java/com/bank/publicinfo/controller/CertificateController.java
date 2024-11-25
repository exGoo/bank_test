package com.bank.publicinfo.controller;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private CertificateServiceImpl certificateService;

    @Autowired
    public void setCertificateService(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable Long id) {
        CertificateDto certificateDto = certificateService.findById(id);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CertificateDto>> getAllCertificates() {
        List<CertificateDto> certificateDto = certificateService.findAll();
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CertificateDto> addCertificate(@RequestBody CertificateDto certificateDto) {
        CertificateDto createdCertificate = certificateService.addCertificate(certificateDto);
        return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CertificateDto> updateCertificate(@PathVariable Long id, @RequestBody CertificateDto certificateDto) {
        CertificateDto updatedCertificate = certificateService.updateCertificate(id, certificateDto);
        return new ResponseEntity<>(updatedCertificate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}