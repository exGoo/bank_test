package com.bank.antifraud.service;

import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import java.util.List;

public interface SuspiciousPhoneTransfersService {

    void add(SuspiciousPhoneTransfers audit);

    SuspiciousPhoneTransfers get(Long id);

    void update(SuspiciousPhoneTransfers audit);

    void remove(Long id);

    List<SuspiciousPhoneTransfers> getAll();

}
