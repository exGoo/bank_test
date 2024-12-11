package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import java.util.List;

public interface SuspiciousCardTransferService {

    void add(SuspiciousCardTransfer sct);

    SuspiciousCardTransfer get(Long id);

    void update(Long id, SuspiciousCardTransfer sct);

    void remove(Long id);

    List<SuspiciousCardTransfer> getAll();
}
