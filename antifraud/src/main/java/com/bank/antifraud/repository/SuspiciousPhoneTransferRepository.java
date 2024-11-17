package com.bank.antifraud.repository;

import com.bank.antifraud.model.SuspiciousPhoneTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuspiciousPhoneTransferRepository extends JpaRepository<SuspiciousPhoneTransfer, Long> {
}