package com.bank.publicinfo.service;

import com.bank.publicinfo.repository.ATMRepository;
import com.bank.publicinfo.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {
    private ATMRepository atmRepository;
    private BranchRepository branchRepository;

    @Autowired
    public void setAtmRepository(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Autowired
    public void setBranchRepository(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }
}
