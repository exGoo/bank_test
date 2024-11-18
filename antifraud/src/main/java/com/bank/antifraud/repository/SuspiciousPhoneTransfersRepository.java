package com.bank.antifraud.repository;

import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuspiciousPhoneTransfersRepository extends JpaRepository<SuspiciousPhoneTransfers, Long> {
}
