package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.CertificateDto;
import java.util.List;

public interface CertificateService {

    CertificateDto findById(Long id);

    List<CertificateDto> findAll();

    CertificateDto addCertificate(CertificateDto certificate);

    CertificateDto updateCertificate(Long id, CertificateDto certificate);

    void deleteCertificateById(Long id);
}
