package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b " +
            "LEFT JOIN FETCH b.atms")
    List<Branch> findAllWithAtms();
}
