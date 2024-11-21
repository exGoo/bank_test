package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseServiceImpl {
    private LicenseRepository licenseRepository;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }
}
