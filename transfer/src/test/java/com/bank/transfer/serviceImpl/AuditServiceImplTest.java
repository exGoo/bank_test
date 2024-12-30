package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.mapper.AuditMapper;
import com.bank.transfer.repository.AuditRepository;
import com.bank.transfer.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.audit1;
import static com.bank.transfer.ResourcesForTests.audit2;
import static com.bank.transfer.ResourcesForTests.auditDTO1;
import static com.bank.transfer.ResourcesForTests.auditDTO2;
import static com.bank.transfer.ResourcesForTests.auditListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {
    @Mock
    private AuditRepository auditRepository;
    @Mock
    private AuditMapper mapper;
    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    void getAuditById() {
        when(auditRepository.findById(ID_1)).thenReturn(Optional.of(audit1));
        when(mapper.auditToAuditDTO(audit1)).thenReturn(auditDTO1);
        Optional<AuditDTO> optionalAuditDTO = auditService.getAuditById(ID_1);
        AuditDTO auditDTO = optionalAuditDTO.get();
        assertNotNull(auditDTO);
        assertEquals(auditDTO1, auditDTO);
    }

    @Test
    void allAudit() {
        when(auditRepository.findAll())
                .thenReturn(List.of(audit1, audit2));
        when(mapper.auditListToDTOList(List.of(audit1, audit2)))
                .thenReturn(List.of(auditDTO1, auditDTO2));
        List<AuditDTO> allAuditsDTO = auditService.allAudit();
        assertEquals(allAuditsDTO, auditListDTO);
    }
}
