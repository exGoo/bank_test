package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.mapper.AuditMapper;
import com.bank.transfer.repository.AuditRepository;
import com.bank.transfer.service.impl.AuditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    AuditDTO auditDTO1;
    AuditDTO auditDTO2;
    Audit audit1;
    Audit audit2;
    List<Audit> auditList = new ArrayList<>();
    List<AuditDTO> auditListDTO = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        audit1 = Audit.builder()
                .id(1L)
                .entityType("AccountTransfer")
                .operationType("UPDATE")
                .createdBy("111")
                .modifiedBy("111")
                .createdAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"))
                .modifiedAt(LocalDateTime.parse("2024-12-22T12:40:28.096895"))
                .newEntityJson("{\"id\":111,\"accountNumber\":314681,\"amount\":347574.34" +
                        ",\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .entityJson("{\"id\":111,\"accountNumber\":311,\"amount\":347574.34," +
                        "\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .build();
        audit2 = Audit.builder()
                .id(2L)
                .entityType("AccountTransfer")
                .operationType("UPDATE")
                .createdBy("11")
                .modifiedBy("11")
                .createdAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"))
                .modifiedAt(LocalDateTime.parse("2024-12-22T12:40:28.096895"))
                .newEntityJson("{\"id\":111,\"accountNumber\":314681,\"amount\":347574.34" +
                        ",\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .entityJson("{\"id\":111,\"accountNumber\":311,\"amount\":347574.34," +
                        "\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .build();
        auditDTO1 = AuditDTO.builder()
                .id(1L)
                .entityType("AccountTransfer")
                .operationType("UPDATE")
                .createdBy("111")
                .modifiedBy("111")
                .createdAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"))
                .modifiedAt(LocalDateTime.parse("2024-12-22T12:40:28.096895"))
                .newEntityJson("{\"id\":111,\"accountNumber\":314681,\"amount\":347574.34" +
                        ",\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .entityJson("{\"id\":111,\"accountNumber\":311,\"amount\":347574.34," +
                        "\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .build();
        auditDTO2 = AuditDTO.builder()
                .id(2L)
                .entityType("AccountTransfer")
                .operationType("UPDATE")
                .createdBy("11")
                .modifiedBy("11")
                .createdAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"))
                .modifiedAt(LocalDateTime.parse("2024-12-22T12:40:28.096895"))
                .newEntityJson("{\"id\":111,\"accountNumber\":314681,\"amount\":347574.34" +
                        ",\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .entityJson("{\"id\":111,\"accountNumber\":311,\"amount\":347574.34," +
                        "\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}")
                .build();

        auditList.add(audit1);
        auditList.add(audit2);

        auditListDTO.add(auditDTO1);
        auditListDTO.add(auditDTO2);
    }

    @Test
    void getAuditById() {
        when(auditRepository.findById(1L)).thenReturn(Optional.of(audit1));
        when(mapper.auditToAuditDTO(audit1)).thenReturn(auditDTO1);
        Optional<AuditDTO> optionalAuditDTO = auditService.getAuditById(1L);
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
