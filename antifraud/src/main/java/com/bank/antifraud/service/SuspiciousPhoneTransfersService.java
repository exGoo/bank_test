package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import java.util.List;

public interface SuspiciousPhoneTransfersService {

    void add(SuspiciousPhoneTransfersDto sat);

    SuspiciousPhoneTransfersDto get(Long id);

    void update(Long id, SuspiciousPhoneTransfersDto sat);

    void remove(Long id);

    List<SuspiciousPhoneTransfersDto> getAll();
}
