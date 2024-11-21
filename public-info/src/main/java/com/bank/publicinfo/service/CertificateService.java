package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Certificate;


public interface CertificateService {
    Certificate addCertificate(Certificate certificate);

    void deleteCertificate(Certificate certificate);

}
