package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
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
        log.info("Попытка получения лицензии по id: {}", id);
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Информация о лицензии с id: {} не найдена", id);
                    return new EntityNotFoundException("License not found with id " + id);
                });
        return licenseMapper.toDto(license);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseDto> findAll() {
        log.info("Попытка получения списка всех лицензий");
        List<License> licenseList = licenseRepository.findAll();
        log.info("Список лицензий успешно получен");
        return licenseList.stream().map(licenseMapper::toDto).toList();
    }

    @Override
    public LicenseDto addLicence(LicenseDto license) {
        try {
            log.info("Попытка добавления лицензии со следующей информацией {}", license);
            License newLicense = licenseMapper.toModel(license);
            License saveLicense = licenseRepository.save(newLicense);
            LicenseDto savedDto = licenseMapper.toDto(saveLicense);
            log.info("Лицензия успешно добавлена");
            return savedDto;
        } catch (Exception e) {
            log.error("Ошибка при добавлении лицензии");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    public LicenseDto updateLicense(Long id, LicenseDto dto) {
        log.info("Попытка получения лицензии по id: {}", id);
        License existingLicense = licenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Информация о лицензии с id: {} не найдена", id);
                    return new EntityNotFoundException("License not found with id " + id);
                });
        try {
            log.info("Попытка изменения лицензии с id {}", id);
            if (dto.getPhoto() != null) {
                existingLicense.setPhoto(dto.getPhoto());
            }
            if (dto.getBankDetailsId() != null) {
                BankDetails bankDetails = bankDetailsRepository.findById(dto.getBankDetailsId())
                        .orElseThrow(() -> new EntityNotFoundException("License not found with id " + dto.getBankDetailsId()));
                existingLicense.setBankDetails(bankDetails);
            }
            License updatedLicense = licenseRepository.save(existingLicense);
            log.info("Лицензия успешно изменена");
            return licenseMapper.toDto(updatedLicense);
        } catch (Exception e) {
            log.error("Ошибка при изменении лицензии");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }
    @Override
    public void deleteLicenceById(Long id) {
        try {
            log.info("Попытка удаления лицензии с id: {}", id);
            licenseRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Ошибка при удалении лицензии");
            throw new EntityNotFoundException("License not found with id " + id);
        }

    }

}
