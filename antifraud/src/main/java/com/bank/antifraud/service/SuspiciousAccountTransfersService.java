package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuspiciousAccountTransfersService {

    SuspiciousAccountTransfersDto add(SuspiciousAccountTransfersDto sat);

    SuspiciousAccountTransfersDto get(Long id);

    SuspiciousAccountTransfersDto update(Long id, SuspiciousAccountTransfersDto sat);

    void remove(Long id);

    Page<SuspiciousAccountTransfersDto> getAll(Pageable pageable);
}
