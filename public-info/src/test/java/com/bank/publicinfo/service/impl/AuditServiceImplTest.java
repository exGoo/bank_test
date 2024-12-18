package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.utils.Admin;
import com.bank.publicinfo.utils.Auditable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @InjectMocks
    private AuditServiceImpl auditService;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Admin admin;

    private Auditable<Long> entity;

    @BeforeEach
    public void setUp() {
        entity = mock(Auditable.class);
        when(entity.getEntityName()).thenReturn("ATM");
        when(entity.getEntityId()).thenReturn(TEST_ID_1);
    }

    @Test
    void saveNewAudit_Success() throws Exception {
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(admin.getUsername()).thenReturn("admin");
        auditService.saveNewAudit(entity);
        verify(auditRepository).save(any(Audit.class));
    }

    @Test
    void saveNewAudit_JsonProcessingException() throws Exception {
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> auditService.saveNewAudit(entity));
        assertEquals("Please check the correctness of JSON", exception.getMessage());
    }

    @Test
    void refreshAudit_Success() throws Exception {
        Audit lastAudit = mock(Audit.class);
        when(lastAudit.getEntityJson()).thenReturn("{}");
        when(auditRepository.findByEntityJsonId(any(), any())).thenReturn(Optional.of(lastAudit));
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(admin.getUsername()).thenReturn("admin");
        auditService.refreshAudit(entity);
        verify(auditRepository).save(any(Audit.class));
    }

    @Test
    void refreshAudit_AuditNotFound() {
        when(auditRepository.findByEntityJsonId(any(), any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> auditService.refreshAudit(entity));
    }

    @Test
    void refreshAudit_JsonProcessingException() throws Exception {
        when(auditRepository.findByEntityJsonId(any(), any())).thenReturn(Optional.of(mock(Audit.class)));
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> auditService.refreshAudit(entity));
        assertEquals("Please check the correctness of JSON", exception.getMessage());
    }

}