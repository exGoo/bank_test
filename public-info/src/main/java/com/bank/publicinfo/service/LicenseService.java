package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.LicenseDto;
import java.util.List;

public interface LicenseService {

    LicenseDto findById(Long id);

    List<LicenseDto> findAll();

    LicenseDto addLicence(LicenseDto license);

    LicenseDto updateLicense(Long id, LicenseDto license);

    void deleteLicenceById(Long id);
}

