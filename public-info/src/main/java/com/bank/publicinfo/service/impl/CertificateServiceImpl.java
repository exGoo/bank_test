package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Реализация интерфейса {@link CertificateService}, предоставляющая бизнес-логику для управления сертификатами.
 *
 * <p>Содержит операции:
 * <ul>
 *     <li>Поиск сертификата по ID</li>
 *     <li>Получение всех сертификатов</li>
 *     <li>Добавление нового сертификата</li>
 *     <li>Обновление существующего сертификата</li>
 *     <li>Удаление сертификата по ID</li>
 * </ul>
 *
 * использует {@link CertificateRepository} для взаимодействия с базой данных,
 * а также {@link CertificateMapper} для преобразования моделей в DTO и обратно.

 * Логирование операций осуществляется с помощью библиотеки Lombok (@Slf4j).
 */
@Service
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private CertificateRepository certificateRepository;
    private CertificateMapper certificateMapper;
    private BankDetailsRepository bankDetailsRepository;

    /**
     * Устанавливает репозиторий для работы с сертификатами.
     *
     * @param certificateRepository репозиторий сертификатов
     */
    @Autowired
    public void setCertificateRepository(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    /**
     * Устанавливает маппер для преобразования сертификатов.
     *
     * @param certificateMapper маппер сертификатов
     */
    @Autowired
    public void setCertificateMapper(CertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    /**
     * Устанавливает репозиторий для работы с банковской информацией.
     *
     * @param bankDetailsRepository репозиторий банковских реквизитов
     */
    @Autowired
    public void setBankDetailsRepository(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    /**
     * Находит сертификат по ID.
     *
     * @param id идентификатор сертификата
     * @return DTO сертификата
     * @throws EntityNotFoundException если сертификат не найден
     */
    @Override
    @Transactional(readOnly = true)
    public CertificateDto findById(Long id) {
        log.info("Попытка получения сертификата по id: {}", id);
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Информация о сертификате с id: {} не найдена", id);
                    return new EntityNotFoundException("Certificate not found with id " + id);
                });
        return certificateMapper.toDto(certificate);
    }

    /**
     * Получает список всех сертификатов.
     *
     * @return список DTO сертификатов
     */
    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> findAll() {
        log.info("Попытка получения списка всех сертификатов");
        List<Certificate> certificateList = certificateRepository.findAll();
        log.info("Список сертификатов успешно получен");
        return certificateList.stream().map(certificateMapper::toDto).toList();
    }

    /**
     * Добавляет новый сертификат.
     *
     * @param certificate DTO сертификата для добавления
     * @return сохраненный DTO сертификата
     * @throws DataValidationException если данные некорректны
     */
    @Override
    public CertificateDto addCertificate(CertificateDto certificate) {
        try {
            log.info("Попытка добавления сертификата со следующей информацией {}", certificate);
            Certificate newCertificate = certificateMapper.toModel(certificate);
            Certificate saveCertificate = certificateRepository.save(newCertificate);
            CertificateDto savedDto = certificateMapper.toDto(saveCertificate);
            log.info("Сертификат успешно добавлен");
            return savedDto;
        } catch (Exception e) {
            log.error("Ошибка при добавлении сертификата");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    /**
     * Обновляет существующий сертификат по ID.
     *
     * @param id  идентификатор сертификата
     * @param dto DTO с обновленными данными
     * @return обновленный DTO сертификата
     * @throws EntityNotFoundException если сертификат или банковские реквизиты не найдены
     * @throws DataValidationException если данные некорректны
     */
    @Override
    public CertificateDto updateCertificate(Long id, CertificateDto dto) {
        log.info("Попытка получения сертификата по id: {}", id);
        Certificate existingCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Информация о сертификате с id: {} не найдена", id);
                    return new EntityNotFoundException("Certificate not found with id " + id);
                });
        try {
            log.info("Попытка изменения сертификата с id {}", id);
            if (dto.getPhoto() != null) {
                existingCertificate.setPhoto(dto.getPhoto());
            }
            if (dto.getBankDetailsId() != null) {
                BankDetails bankDetails = bankDetailsRepository.findById(dto.getBankDetailsId())
                        .orElseThrow(() -> new EntityNotFoundException("Certificate not found with id: " + dto.getBankDetailsId()));
                existingCertificate.setBankDetails(bankDetails);
            }
            Certificate updatedCertificate = certificateRepository.save(existingCertificate);
            log.info("Сертификат успешно изменен");
            return certificateMapper.toDto(updatedCertificate);
        } catch (Exception e) {
            log.error("Ошибка при изменении сертификата");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    /**
     * Удаляет сертификат по ID.
     *
     * @param id идентификатор сертификата
     * @throws EntityNotFoundException если сертификат не найден
     */
    @Override
    public void deleteCertificateById(Long id) {
        try {
            log.info("Попытка удаления сертификата с id: {}", id);
            certificateRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Ошибка при удалении сертификата");
            throw new EntityNotFoundException("Certificate not found with id: " + id);
        }
    }
}
