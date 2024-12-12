package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;

import java.util.List;

public interface SuspiciousCardTransferService {

    void add(SuspiciousCardTransferDto sct);

    SuspiciousCardTransferDto get(Long id);

    void update(Long id, SuspiciousCardTransferDto sct);

    void remove(Long id);

    List<SuspiciousCardTransferDto> getAll();
}
