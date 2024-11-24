package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private CertificateRepository certificateRepository;
    private CertificateMapper certificateMapper;
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    public void setCertificateRepository(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Autowired
    public void setCertificateMapper(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Autowired
    public void setBankDetailsRepository(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDto findById(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate not found with id " + id));
        return certificateMapper.toDto(certificate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> findAll() {
        List<Certificate> certificateList = certificateRepository.findAll();
        return certificateList.stream().map(certificateMapper::toDto).toList();
    }

    @Override
    public CertificateDto addCertificate(CertificateDto certificate) {
        try {
            Certificate newCertificate = certificateMapper.toModel(certificate);
            Certificate saveCertificate = certificateRepository.save(newCertificate);
            return certificateMapper.toDto(saveCertificate);
        } catch (Exception e) {
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    public CertificateDto updateCertificate(Long id, CertificateDto dto) {
        Certificate existingCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate not found with id: " + id));
        if (dto.getPhoto() != null) {
            existingCertificate.setPhoto(dto.getPhoto());
        }
        if (dto.getBankDetailsId() != null) {
            BankDetails bankDetails = bankDetailsRepository.findById(dto.getBankDetailsId())
                    .orElseThrow(() -> new EntityNotFoundException("BankDetails not found with id: " + dto.getBankDetailsId()));
            existingCertificate.setBankDetails(bankDetails);
        }
        Certificate updatedCertificate = certificateRepository.save(existingCertificate);
        return certificateMapper.toDto(updatedCertificate);
    }

    @Override
    public void deleteCertificateById(Long id) {
        try {
            certificateRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Certificate not found with id: " + id);
        }

    }
}
