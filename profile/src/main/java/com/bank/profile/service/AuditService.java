package com.bank.profile.service;

import com.bank.profile.dto.AuditDto;

import java.util.List;

public interface AuditService {
    void save(AuditDto audit);
    List<AuditDto> findAll();
    AuditDto findById(Long id);
    void update(Long id,AuditDto audit);
    void deleteById(Long id);

}
