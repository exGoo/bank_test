package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.BankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {
    private BankDetailsRepository bankDetailsRepository;
    private BankDetailsMapper bankDetailsMapper;
    private CertificateRepository certificateRepository;
    private LicenseRepository licenseRepository;
    private LicenseMapper licenseMapper;
    private CertificateMapper certificateMapper;

    @Autowired
    public void setBankDetailsRepository(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @Autowired
    public void setBankDetailsMapper(BankDetailsMapper bankDetailsMapper) {
        this.bankDetailsMapper = bankDetailsMapper;
    }

    @Autowired
    public void setCertificateRepository(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    public void setLicenseMapper(LicenseMapper licenseMapper) {
        this.licenseMapper = licenseMapper;
    }

    @Autowired
    public void setCertificateMapper(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findById(Long id) {
        BankDetails bankDetails = bankDetailsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BankDetails not found"));
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findAllWithRelations() {
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAllWithRelations();
        return bankDetailsList.stream().map(bankDetailsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findByCity(String city) {
        List<BankDetails> bankDetailsList = bankDetailsRepository.findByCityWithRelations(city);
        return bankDetailsList.stream().map(bankDetailsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BankDetailsDto addBankDetails(BankDetailsDto bankDetailsDto) {
        BankDetails bankDetails = bankDetailsMapper.toModel(bankDetailsDto);
        BankDetails saveBankDetails = bankDetailsRepository.save(bankDetails);
        return bankDetailsMapper.toDto(saveBankDetails);
    }

    @Override
    public void deleteBankDetailsById(Long id) {
        bankDetailsRepository.deleteById(id);
    }

    @Override
    public BankDetailsDto updateBankDetails(Long id, BankDetailsDto bankDetailsDto) {

        BankDetails existingBankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BankDetails not found"));

        bankDetailsMapper.updateEntity(existingBankDetails, bankDetailsDto);


        if (bankDetailsDto.getLicenses() != null) {
            for (License license : existingBankDetails.getLicenses()) {
                licenseRepository.delete(license);
            }

            for (LicenseDto licenseDto : bankDetailsDto.getLicenses()) {
                License license = licenseMapper.toModel(licenseDto);
                license.setBankDetails(existingBankDetails);
                licenseRepository.save(license);
            }
        }

        if (bankDetailsDto.getCertificates() != null) {
            for (Certificate certificate : existingBankDetails.getCertificates()) {
                certificateRepository.delete(certificate);
            }
            for (CertificateDto certificateDto : bankDetailsDto.getCertificates()) {
                Certificate certificate = certificateMapper.toModel(certificateDto);
                certificate.setBankDetails(existingBankDetails);
                certificateRepository.save(certificate);
            }
        }

        BankDetails updatedBankDetails = bankDetailsRepository.save(existingBankDetails);

        return bankDetailsMapper.toDto(updatedBankDetails);
    }

}
