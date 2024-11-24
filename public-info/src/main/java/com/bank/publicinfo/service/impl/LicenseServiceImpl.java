package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {
    private LicenseRepository licenseRepository;
    private LicenseMapper licenseMapper;
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setLicenseMapper(LicenseMapper licenseMapper) {
        this.licenseMapper = licenseMapper;
    }

    @Autowired
    public void setBankDetailsRepository(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseDto findById(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("License not found")));
        return licenseMapper.toDto(license);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseDto> findAll() {
        List<License> licenseList = licenseRepository.findAll();
        return licenseList.stream().map(licenseMapper::toDto).toList();
    }

    @Override
    public LicenseDto addLicence(LicenseDto license) {
        try {
            License newLicense = licenseMapper.toModel(license);
            License saveLicense = licenseRepository.save(newLicense);
            return licenseMapper.toDto(saveLicense);
        } catch (Exception e) {
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    public LicenseDto updateLicense(Long id, LicenseDto dto) {
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("License not found with id: " + id));
        if (dto.getPhoto() != null) {
            existingLicense.setPhoto(dto.getPhoto());
        }
        if (dto.getBankDetailsId() != null) {
            BankDetails bankDetails = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("BankDetails not found with id: " + dto.getBankDetailsId()));
            existingLicense.setBankDetails(bankDetails);
        }
        License updatedLicense = licenseRepository.save(existingLicense);
        return licenseMapper.toDto(updatedLicense);
    }

    @Override
    public void deleteLicenceById(Long id) {
        try {
            licenseRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("License not found with id: " + id);
        }

    }

}
