package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.BankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
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
        BankDetails bankDetails = bankDetailsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BankDetails not found with id " + id));
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findAllWithRelations() {
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        return bankDetailsList.stream().map(bankDetailsMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDetailsDto> findByCity(String city) {
        List<BankDetails> bankDetailsList = bankDetailsRepository.findByCityWithRelations(city);
        return bankDetailsList.stream().map(bankDetailsMapper::toDto).toList();
    }

    @Override
    @Transactional
    public BankDetailsDto addBankDetails(BankDetailsDto bankDetailsDto) {
        try {
            BankDetails bankDetails = bankDetailsMapper.toModel(bankDetailsDto);
            BankDetails saveBankDetails = bankDetailsRepository.save(bankDetails);
            return bankDetailsMapper.toDto(saveBankDetails);
        } catch (Exception e) {
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    @Transactional
    public void deleteBankDetailsById(Long id) {
        try {
            bankDetailsRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("BankDetails not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public BankDetailsDto updateBankDetails(Long id, BankDetailsDto dto) {
        BankDetails existingBankDetails = bankDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BankDetails not found with id: " + id));
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
        return bankDetailsMapper.toDto(updatedBankDetails);
    }
}



