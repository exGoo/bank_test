package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    @Query(value = "SELECT * FROM public_bank_information.audit AS a WHERE a.entity_json LIKE %:id% " +
            "AND a.entity_type LIKE %:entityType% " +
            "ORDER BY a.modified_at DESC LIMIT 1", nativeQuery = true)
    Optional<Audit> findByEntityJsonId(@Param("id") String id, @Param("entityType") String entityType);

}
