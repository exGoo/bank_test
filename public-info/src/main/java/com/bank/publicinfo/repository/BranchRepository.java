package com.bank.publicinfo.repository;


import com.bank.publicinfo.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b " +
            "LEFT JOIN FETCH b.atms")
    List<Branch> findAllWithAtms();

    @Query("SELECT b FROM Branch b LEFT JOIN FETCH b.atms WHERE b.city = :city")
    List<Branch> findByCityWithAtms(@Param("city") String city);
}
