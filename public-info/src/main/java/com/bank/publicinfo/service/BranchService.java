package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;

import java.util.List;

public interface BranchService {
    Branch findById(Long id);

    List<BranchDto> findAllWithATMs();

    List<BranchDto> findByCity(String city);

    BranchDto addBranch(BranchDto branch);

    void deleteBranchById(Long id);

    BranchDto updateBranch(Long id, BranchDto branch);


}
