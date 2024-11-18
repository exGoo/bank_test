package com.bank.antifraud.repository;

import com.bank.antifraud.model.SuspiciousCardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuspiciousCardTransferRepository extends JpaRepository<SuspiciousCardTransfer, Long> {
}
