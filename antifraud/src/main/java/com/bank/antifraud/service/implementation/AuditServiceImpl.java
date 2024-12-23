package com.bank.antifraud.service.implementation;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.exception.NotFoundAuditException;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    @Transactional
    public Audit save(Audit audit) {
        return auditRepository.save(audit);
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
                .orElseThrow(() -> new NotFoundAuditException(id));
    }
}
