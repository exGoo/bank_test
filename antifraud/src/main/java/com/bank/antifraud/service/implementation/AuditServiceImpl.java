package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundAuditException;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.model.Audit;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void add(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Audit get(Long id) {
        final Optional<Audit> optional = auditRepository.findById(id);
        return optional.orElseThrow(() -> new NotFoundAuditException(id));
    }

    @Override
    public void update(Audit audit) {
        if (!auditRepository.existsById(audit.getId())) {
            throw new NotFoundSuspiciousAccountTransfersException(audit.getId());
        }
        auditRepository.save(audit);
    }

    @Override
    public void remove(Long id) {
        if (!auditRepository.existsById(id)) {
            throw new NotFoundSuspiciousAccountTransfersException(id);
        }
        auditRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> getAll() {
        return auditRepository.findAll();
    }

}
