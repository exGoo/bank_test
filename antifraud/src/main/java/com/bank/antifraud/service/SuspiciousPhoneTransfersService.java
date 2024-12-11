package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import java.util.List;

public interface SuspiciousPhoneTransfersService {

    void add(SuspiciousPhoneTransfers sat);

    SuspiciousPhoneTransfers get(Long id);

    void update(Long id, SuspiciousPhoneTransfers sat);

    void remove(Long id);

    List<SuspiciousPhoneTransfers> getAll();
}
