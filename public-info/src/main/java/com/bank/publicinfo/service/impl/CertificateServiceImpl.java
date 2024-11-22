package com.bank.publicinfo.service.impl;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private CertificateRepository certificateRepository;
    private CertificateMapper certificateMapper;

    @Autowired
    public void setCertificateRepository(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }
    @Autowired
    public void setCertificateMapper(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Override
    public CertificateDto findById(Long id) {
        return null;
    }

    @Override
    public List<CertificateDto> findAll() {
        return List.of();
    }

    @Override
    public CertificateDto addCertificate(CertificateDto certificate) {
        return null;
    }

    @Override
    public void deleteCertificateById(Long id) {

    }
}
