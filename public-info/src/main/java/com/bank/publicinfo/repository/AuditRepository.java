package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    Optional<Audit> findTopByNewEntityJson(String entityJson);

    Optional<Audit> findByEntityJson(String entityJson);

}
