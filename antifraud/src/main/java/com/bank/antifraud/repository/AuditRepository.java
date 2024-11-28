package com.bank.antifraud.repository;

import com.bank.antifraud.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    Optional<Audit> findTopByNewEntityJson(String entityJson);

    Optional<Audit> findByEntityJson(String entityJson);

}
