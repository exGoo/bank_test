package com.bank.antifraud.service;

import com.bank.antifraud.model.SuspiciousCardTransfer;

import java.util.List;

public interface SuspiciousCardTransferService {

    void add(SuspiciousCardTransfer sct);

    SuspiciousCardTransfer get(Long id);

    void update(Long id, SuspiciousCardTransfer sct);

    void remove(Long id);

    List<SuspiciousCardTransfer> getAll();

}
