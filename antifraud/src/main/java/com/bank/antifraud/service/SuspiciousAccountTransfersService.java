package com.bank.antifraud.service;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import java.util.List;

public interface SuspiciousAccountTransfersService {

    void add(SuspiciousAccountTransfers sat);

    SuspiciousAccountTransfers get(Long id);

    void update(Long id, SuspiciousAccountTransfers sat);

    void remove(Long id);

    List<SuspiciousAccountTransfers> getAll();

}
