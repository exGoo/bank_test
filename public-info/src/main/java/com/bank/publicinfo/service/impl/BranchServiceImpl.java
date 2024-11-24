package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
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
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Branch not found with id " + id));
        return branchMapper.toDto(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> findAllWithATMs() {
        List<Branch> branchList = branchRepository.findAll();
        return branchList.stream().map(branchMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchDto> findByCity(String city) {
        List<Branch> branchList = branchRepository.findByCityWithAtms(city);
        return branchList.stream().map(branchMapper::toDto).toList();

    }

    @Override
    @Transactional
    public BranchDto addBranch(BranchDto branch) {
        try {
            Branch newBranch = branchMapper.toModel(branch);
            Branch saveBranch = branchRepository.save(newBranch);
            return branchMapper.toDto(saveBranch);
        } catch (Exception e) {
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    @Transactional
    public void deleteBranchById(Long id) {
        try {
            branchRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Branch not found with id: " + id);
        }

    }

    @Override
    @Transactional
    public BranchDto updateBranch(Long id, BranchDto dto) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
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
        return branchMapper.toDto(updatedBranch);
    }
}
