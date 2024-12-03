package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    List<BankDetails> findByCity(@Param("city") String city);
}
