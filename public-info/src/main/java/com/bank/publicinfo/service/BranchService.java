package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> findAllWithATMs();
    Branch findById(Long id);

    List<Branch> findByCity(String city);

    Branch addBranch(Branch branch);

    Branch deleteBranchById(Long id);

    Branch updateBranch(Long id, Branch branch);

}
