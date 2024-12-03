package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.BankDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class BankDetailsServiceImpl implements BankDetailsService {

    private BankDetailsRepository bankDetailsRepository;
    private BankDetailsMapper bankDetailsMapper;

    @Autowired
    public void setBankDetailsRepository(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @Autowired
    public void setBankDetailsMapper(BankDetailsMapper bankDetailsMapper) {
        this.bankDetailsMapper = bankDetailsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BankDetailsDto findById(Long id) {
        log.info("Попытка получения информации о банке по id: {}", id);
        BankDetails bankDetails = bankDetailsRepository.findById(id).orElseThrow(() -> {
            log.error("Информация о банке с id: {} не найдена", id);
            return new EntityNotFoundException("Bank Details not found with id " + id);
        });
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findAllWithRelations() {
        log.info("Попытка получения списка информации о банках, включающей id связанных сертификатов и лицензий");
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        log.info("Список информации о банках успешно получен");
        return bankDetailsList.stream().map(bankDetailsMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findByCity(String city) {

        log.info("Попытка получения информации о банках в городе {} , включающей id связанных сертификатов и лицензий", city);
        try {
            List<BankDetails> bankDetailsList = bankDetailsRepository.findByCity(city);
            if (bankDetailsList.isEmpty()) {
                throw new RuntimeException();
            }
            log.info("Список информации о банках успешно получен");
            return bankDetailsList.stream().map(bankDetailsMapper::toDto).toList();
        } catch (Exception e) {
            log.error("Ошибка при поиске информации о банках в городе {}", city);
            throw new EntityNotFoundException("City not found " + city);
        }
    }

    @Override
    @Transactional
    public BankDetailsDto addBankDetails(BankDetailsDto bankDetailsDto) {
        try {
            log.info("Попытка добавлении информации о банке со следующими данными {}", bankDetailsDto);
            BankDetails bankDetails = bankDetailsMapper.toModel(bankDetailsDto);
            BankDetails saveBankDetails = bankDetailsRepository.save(bankDetails);
            BankDetailsDto savedDto = bankDetailsMapper.toDto(saveBankDetails);

            log.info("Информация о банке успешно добавлена");
            return savedDto;
        } catch (Exception e) {
            log.error("Ошибка при добавлении информации о банках");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    @Transactional
    public void deleteBankDetailsById(Long id) {
        try {
            log.info("Попытка удаления информации о банке с id: {}", id);
            bankDetailsRepository.deleteById(id);
        } catch (Exception e) {
            log.info("Ошибка при удалении информации о банке");
            throw new EntityNotFoundException("BankDetails not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public BankDetailsDto updateBankDetails(Long id, BankDetailsDto dto) {
        BankDetails existingBankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Информация о банке с id: {} не найдена", id);
                    return new EntityNotFoundException("Bank Details not found with id " + id);
                });
        try {
            if(dto.getId()!= null){
                existingBankDetails.setId(dto.getId());
            }
            if (dto.getBik() != null) {
                existingBankDetails.setBik(dto.getBik());
            }
            if (dto.getInn() != null) {
                existingBankDetails.setInn(dto.getInn());
            }
            if (dto.getKpp() != null) {
                existingBankDetails.setKpp(dto.getKpp());
            }
            if (dto.getCorAccount() != null) {
                existingBankDetails.setCorAccount(dto.getCorAccount());
            }
            if (dto.getCity() != null) {
                existingBankDetails.setCity(dto.getCity());
            }
            if (dto.getJointStockCompany() != null) {
                existingBankDetails.setJointStockCompany(dto.getJointStockCompany());
            }
            if (dto.getName() != null) {
                existingBankDetails.setName(dto.getName());
            }

            BankDetails updatedBankDetails = bankDetailsRepository.save(existingBankDetails);
            log.info("Информация о банке успешно изменена");
            return bankDetailsMapper.toDto(updatedBankDetails);
        } catch (Exception e) {
            log.error("Произошла ошибка при изменении банковской информации");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }
}



