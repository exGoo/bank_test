package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    @Query("SELECT b FROM BankDetails b " +
            "LEFT JOIN FETCH b.licenses " +
            "LEFT JOIN FETCH b.certificates")
    List<BankDetails> findAllWithRelations();
}
