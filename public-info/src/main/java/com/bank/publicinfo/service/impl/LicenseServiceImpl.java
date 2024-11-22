package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {
    private LicenseRepository licenseRepository;
    private LicenseMapper licenseMapper;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setLicenseMapper(LicenseMapper licenseMapper) {
        this.licenseMapper = licenseMapper;
    }

    @Override
    public LicenseDto findById(Long id) {
        return null;
    }

    @Override
    public List<LicenseDto> findAll() {
        return List.of();
    }

    @Override
    public LicenseDto addLicence(LicenseDto license) {
        return null;
    }

    @Override
    public void deleteLicenceById(Long id) {

    }

}
