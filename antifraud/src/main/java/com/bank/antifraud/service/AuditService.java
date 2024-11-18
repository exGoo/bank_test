package com.bank.antifraud.service;

import com.bank.antifraud.model.Audit;
import java.util.List;

public interface AuditService {

    void add(Audit audit);

    Audit get(Long id);

    void update(Audit audit);

    void remove(Long id);

    List<Audit> getAll();
}
