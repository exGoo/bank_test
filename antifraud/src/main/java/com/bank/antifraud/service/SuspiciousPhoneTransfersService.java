package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SuspiciousPhoneTransfersService {

    SuspiciousPhoneTransfersDto add(SuspiciousPhoneTransfersDto sat);

    SuspiciousPhoneTransfersDto get(Long id);

    SuspiciousPhoneTransfersDto update(Long id, SuspiciousPhoneTransfersDto sat);

    void remove(Long id);

    Page<SuspiciousPhoneTransfersDto> getAll(Pageable pageable);
}
