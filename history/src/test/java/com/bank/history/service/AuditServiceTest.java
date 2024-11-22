package com.bank.history.service;

import com.bank.history.model.Audit;
import com.bank.history.repository.AuditRepository;
import com.bank.history.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditRepository repository;

    @InjectMocks
    private AuditServiceImpl service;

    @Test
    void createAudit() {
        Audit audit = new Audit();
        service.createAudit(audit);
        verify(repository).save(audit);
    }
}