package com.bank.antifraud.repository;

import com.bank.antifraud.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    Optional<Audit> findAuditByEntityTypeAndOperationTypeAndEntityJsonContaining(String entityType,
                                                                                 String operationType,
                                                                                 String entityJson);
}
