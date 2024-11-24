package com.bank.profile.repository;

import com.bank.profile.entity.Audit;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    @Query(value = "SELECT * FROM profile.audit " +
            "WHERE operation_type = 'create' " +
            "AND entity_type = ?1 " +
            "AND cast((cast(entity_json AS JSON) ->> 'id')AS BIGINT) = ?2",
            nativeQuery = true)
    Optional<Audit> findCreateAuditRecordByEntityAndId(String entityType,
                                                       Long id);
}
