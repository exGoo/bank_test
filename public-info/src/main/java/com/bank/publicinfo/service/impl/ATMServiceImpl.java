package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.ATMMapper;
import com.bank.publicinfo.repository.ATMRepository;
import com.bank.publicinfo.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ATMServiceImpl implements ATMService {

    private ATMRepository atmRepository;
    private ATMMapper atmMapper;

    @Autowired
    public void setAtmRepository(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Autowired
    public void setAtmMapper(ATMMapper atmMapper) {
        this.atmMapper = atmMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ATMDto findById(Long id) {
        ATM atm = atmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ATM not found with id " + id));
        return atmMapper.toDto(atm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ATMDto> findAll() {
        List<ATM> atmList = atmRepository.findAll();
        return atmList.stream().map(atmMapper::toDto).toList();
    }

    @Override
    @Transactional
    public ATMDto addATM(ATMDto atm) {
        try {
            ATM newAtm = atmMapper.toModel(atm);
            ATM saveAtm = atmRepository.saveAndFlush(newAtm);
            ATMDto savedDto = atmMapper.toDto(saveAtm);
            log.info("Банкомат успешно добавлен");
            return savedDto;
        } catch (Exception e) {
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    @Transactional
    public void deleteATMById(Long id) {
        try {
            atmRepository.deleteById(id);
            log.info("Банкомат успешно удален");
        } catch (Exception e) {
            throw new EntityNotFoundException("ATM not found with id " + id);
        }
    }

    @Override
    @Transactional
    public ATMDto updateATM(Long id, ATMDto dto) {
        ATM existingATM = atmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ATM not found with id " + id));
        if (dto.getAddress() != null) {
            existingATM.setAddress(dto.getAddress());
        }
        if (dto.getStartOfWork() != null) {
            existingATM.setStartOfWork(dto.getStartOfWork());
        }
        if (dto.getEndOfWork() != null) {
            existingATM.setEndOfWork(dto.getEndOfWork());
        }
        if (dto.getAllHours() != null) {
            existingATM.setAllHours(dto.getAllHours());
        }
        ATM updatedATM = atmRepository.save(existingATM);
        return atmMapper.toDto(updatedATM);
    }

}
