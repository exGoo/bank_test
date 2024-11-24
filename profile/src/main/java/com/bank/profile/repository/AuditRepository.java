package com.bank.profile.repository;

import com.bank.profile.entity.Audit;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    @Query("SELECT a FROM Audit a " +
            "WHERE a.operationType = 'create' " +
            "AND a.entityType = :entityType " +
            "AND json_extract_path_text(a.entityJson, 'id') = :id")
    Optional<Audit> findCreateAuditRecordByEntityAndId(@Param("entityType") String entityType,
                                                       @Param("id") Long id);
}
