package com.bank.profile.service;

import com.bank.profile.entity.Audit;

import java.util.List;

public interface AuditService {
    void save(Audit audit);
    List<Audit> findAll();
    Audit findById(Long id);
    void update(Long id,Audit audit);
    void deleteById(Long id);

}
