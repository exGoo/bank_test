package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@Slf4j
public class BranchServiceImpl implements BranchService {
    private BranchRepository branchRepository;
    private BranchMapper branchMapper;

    @Autowired
    public void setBranchRepository(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Autowired
    public void setBranchMapper(BranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public BranchDto findById(Long id) {
        log.info("Попытка получения отделения по id: {}", id);
        Branch branch = branchRepository.findById(id).orElseThrow(() -> {
            log.error("Отделение с id: {} не найдено", id);
            return new EntityNotFoundException("ATM not found with id " + id);
        });
        return branchMapper.toDto(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> findAllWithATMs() {
        log.info("Попытка получения списка всех отделений");
        List<Branch> branchList = branchRepository.findAll();
        log.info("Список всех отделений успешно получен");
        return branchList.stream().map(branchMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> findByCity(String city) {
        log.info("Попытка поиска отделений в городе {}", city);
        try {
            List<Branch> branchList = branchRepository.findByCityWithAtms(city);
            if (branchList.isEmpty()) {
                throw new RuntimeException();
            }
            log.info("Список отделений в городе {} успешно получен", city);
            return branchList.stream().map(branchMapper::toDto).toList();

        } catch (Exception e) {
            log.error("Ошибка при поиске отделений в городе {}", city);
            throw new EntityNotFoundException("City not found " + city);
        }
    }

    @Override
    @Transactional
    public BranchDto addBranch(BranchDto branch) {
        try {
            log.info("Попытка добавления отделения со следующими данными {}", branch);
            Branch newBranch = branchMapper.toModel(branch);
            Branch saveBranch = branchRepository.save(newBranch);
            return branchMapper.toDto(saveBranch);
        } catch (Exception e) {
            log.error("Ошибка при добавлении отделения");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    @Transactional
    public void deleteBranchById(Long id) {
        try {
            log.info("Попытка удаления отделения с id: {}", id);
            branchRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Ошибка при удалении отделения");
            throw new EntityNotFoundException("Branch not found with id: " + id);
        }

    }

    @Override
    @Transactional
    public BranchDto updateBranch(Long id, BranchDto dto) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Отделение с id: {} не найдено", id);
                    return new EntityNotFoundException("ATM not found with id " + id);
                });
        try {
            log.info("Попытка изменения отделения с id:{}", id);
            if (dto.getAddress() != null) {
                existingBranch.setAddress(dto.getAddress());
            }
            if (dto.getPhoneNumber() != null) {
                existingBranch.setPhoneNumber(dto.getPhoneNumber());
            }
            if (dto.getCity() != null) {
                existingBranch.setCity(dto.getCity());
            }
            if (dto.getStartOfWork() != null) {
                existingBranch.setStartOfWork(dto.getStartOfWork());
            }
            if (dto.getEndOfWork() != null) {
                existingBranch.setEndOfWork(dto.getEndOfWork());
            }
            Branch updatedBranch = branchRepository.save(existingBranch);
            log.info("Отделение успешно изменено");
            return branchMapper.toDto(updatedBranch);
        } catch (Exception e) {
            log.error("Ошибка при изменении отделения");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }
}
