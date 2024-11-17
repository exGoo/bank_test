package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartificateRepository extends JpaRepository<Certificate, Long> {
}
