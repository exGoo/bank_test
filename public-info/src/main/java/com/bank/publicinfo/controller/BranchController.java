package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.impl.BranchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    private BranchServiceImpl branchService;

    @Autowired
    public void setBranchService(BranchServiceImpl branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        BranchDto branchDto = branchService.findById(id);
        return new ResponseEntity<>(branchDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranch() {
        List<BranchDto> branchDto = branchService.findAllWithATMs();
        return new ResponseEntity<>(branchDto, HttpStatus.OK);
    }


    @GetMapping("/city/{city}")
    public ResponseEntity<List<BranchDto>> getBranchByCity(@PathVariable String city) {
        List<BranchDto> branchDtos = branchService.findByCity(city);
        return new ResponseEntity<>(branchDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BranchDto> addBranch(@RequestBody BranchDto branchDto) {
        BranchDto createdBankDetails = branchService.addBranch(branchDto);
        return new ResponseEntity<>(createdBankDetails, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto bankDetailsDto) {
        BranchDto updatedBranch = branchService.updateBranch(id, bankDetailsDto);
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranchById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
