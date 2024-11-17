package com.bank.publicinfo.service;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankDetailsService {
    private CertificateRepository certificateRepository;
    private LicenseRepository licenseRepository;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setCertificateRepository(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }
}
