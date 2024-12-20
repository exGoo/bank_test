package com.bank.antifraud.service.implementation;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.NotFoundAuditException;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    @Transactional
    public void save(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Audit getFirstAudit(String entityType, String operationType, Long id) {
        return auditRepository.findAuditByEntityTypeAndOperationTypeAndEntityJsonContaining(
                entityType,
                operationType,
                String.format("\"id\":%s", id))
                    .orElseThrow(() -> new NotFoundAuditException(entityType, operationType, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Audit get(Long id) {
        return auditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audit with id: " + id + " not found"));
    }
}
