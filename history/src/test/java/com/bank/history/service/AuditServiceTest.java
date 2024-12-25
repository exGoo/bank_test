package com.bank.history.service;

import com.bank.history.dto.AuditDto;
import com.bank.history.mapper.AuditMapper;
import com.bank.history.model.Audit;
import com.bank.history.repository.AuditRepository;
import com.bank.history.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {
    private static final Audit AUDIT = new Audit();
    private static final AuditDto AUDIT_DTO = new AuditDto();

    @Mock
    private AuditRepository repository;

    @Mock
    private AuditMapper mapper;

    @InjectMocks
    private AuditServiceImpl service;

    @Test
    void createAudit() {
        when(repository.save(AUDIT))
                .thenReturn(AUDIT);
        when(mapper.dtoToAudit(AUDIT_DTO))
                .thenReturn(AUDIT);
        service.createAudit(AUDIT_DTO);
        verify(repository).save(AUDIT);
    }
}
