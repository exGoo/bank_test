package com.bank.publicinfo.service;


import com.bank.publicinfo.entity.License;

public interface LicenseService {
    License addLicence(License license);

    void deleteLicence(License license);
}

