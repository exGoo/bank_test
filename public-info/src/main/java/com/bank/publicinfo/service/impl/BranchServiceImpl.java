package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import com.bank.publicinfo.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<BranchDto> findAllWithATMs() {
        return List.of();
    }

    @Override
    public Branch findById(Long id) {
        return null;
    }

    @Override
    public List<BranchDto> findByCity(String city) {
        return List.of();
    }

    @Override
    public BranchDto addBranch(BranchDto branch) {
        return null;
    }

    @Override
    public void deleteBranchById(Long id) {

    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branch) {
        return null;
    }
}
