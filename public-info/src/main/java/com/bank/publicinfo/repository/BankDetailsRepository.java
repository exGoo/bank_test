package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {

    @Query("SELECT DISTINCT b FROM BankDetails b " +
            "LEFT JOIN b.licenses l " +
            "LEFT JOIN b.certificates c " +
            "WHERE b.city = :city")
    List<BankDetails> findByCityWithRelations(@Param("city") String city);

}
