package com.bank.antifraud.service;

import com.bank.antifraud.model.SuspiciousCardTransfer;
import java.util.List;

public interface SuspiciousCardTransferService {

    void add(SuspiciousCardTransfer scd);

    SuspiciousCardTransfer get(Long id);

    void update(SuspiciousCardTransfer scd);

    void remove(Long id);

    List<SuspiciousCardTransfer> getAll();

}
