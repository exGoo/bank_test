package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.ATMMapper;
import com.bank.publicinfo.repository.ATMRepository;
import com.bank.publicinfo.service.ATMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
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
        log.info("Попытка получения банкомата по id: {}", id);
        ATM atm = atmRepository.findById(id).orElseThrow(() -> {
            log.error("Банкомат с id: {} не найден", id);
            return new EntityNotFoundException("ATM not found with id " + id);
        });
        return atmMapper.toDto(atm);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ATMDto> findAll() {
        log.info("Попытка получения списка всех банкоматов");
        List<ATM> atmList = atmRepository.findAll();
        log.info("Список банкоматов получен успешно");
        return atmList.stream().map(atmMapper::toDto).toList();
    }

    @Override
    @Transactional
    public ATMDto addATM(ATMDto atm) {
        try {
            log.info("Попытка добавления нового банкомата со следующими данными: {}", atm);
            ATM newAtm = atmMapper.toModel(atm);
            ATM saveAtm = atmRepository.save(newAtm);
            return atmMapper.toDto(saveAtm);
        } catch (Exception e) {
            log.error("Произошла ошибка при добавлении банкомата");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    @Transactional
    public void deleteATMById(Long id) {
        try {
            log.info("Попытка удаления банкомата с id: {}", id);
            atmRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Произошла ошибка при удалении банкомата, id: {} отсутствует в базе", id);
            throw new EntityNotFoundException("ATM not found with id " + id);
        }
    }

    @Override
    @Transactional
    public ATMDto updateATM(Long id, ATMDto dto) {
        log.info("Попытка получения банкомата по id: {}", id);
        ATM existingATM = atmRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Банкомат с id: {} не найден", id);
                    return new EntityNotFoundException("ATM not found with id " + id);
                });
        try {
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
            log.info("Банкомат успешно изменен");
            return atmMapper.toDto(updatedATM);
        } catch (Exception e) {
            log.error("Произошла ошибка при изменении банкомата");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }
}
