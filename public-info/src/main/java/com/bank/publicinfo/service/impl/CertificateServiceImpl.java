package com.bank.publicinfo.service.impl;
import com.bank.publicinfo.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl {
    private CertificateRepository certificateRepository;

    @Autowired
    public void setCertificateRepository(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

}
