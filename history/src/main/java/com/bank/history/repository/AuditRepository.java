package com.bank.history.repository;

import com.bank.history.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findByCreatedBy(String createdBy);
}
