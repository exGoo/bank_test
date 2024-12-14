package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuspiciousCardTransferService {

    SuspiciousCardTransferDto add(SuspiciousCardTransferDto sct);

    SuspiciousCardTransferDto get(Long id);

    SuspiciousCardTransferDto update(Long id, SuspiciousCardTransferDto sct);

    void remove(Long id);

    Page<SuspiciousCardTransferDto> getAll(Pageable pageable);
}
