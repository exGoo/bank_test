package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;

import java.util.List;

public interface SuspiciousAccountTransfersService {

    SuspiciousAccountTransfersDto add(SuspiciousAccountTransfersDto sat);

    SuspiciousAccountTransfersDto get(Long id);

    void update(Long id, SuspiciousAccountTransfersDto sat);

    void remove(Long id);

    List<SuspiciousAccountTransfersDto> getAll();
}
