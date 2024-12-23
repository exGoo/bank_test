package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.AuditDTO;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.service.impl.AuditServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditRestController.class)
class AuditRestControllerTest {

    private static final Long ID1 = 1L;
    private static final Long ID2 = 2L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditServiceImpl auditService;

    @MockBean
    private AuditAspect auditAspect;

    ObjectMapper objectMapper = new ObjectMapper();
    AuditDTO auditDTO1;
    AuditDTO auditDTO2;
    Audit audit1;
    Audit audit2;
    List<Audit> auditList = new ArrayList<>();
    List<AuditDTO> auditListDTO = new ArrayList<>();


    @BeforeEach
    public void creatNewAudit() {
        audit1 = Audit.builder()
                .id(ID1)
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
                .id(ID2)
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
                .id(ID1)
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
                .id(ID2)
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

//    @Test
//    void getAccountTransferByIdTest() throws Exception {
//        when(auditService.getAuditById(ID)).thenReturn(Optional.of(auditDTO1));
//        String responseContent = mockMvc.perform(get("/audit/{ID}", ID)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        Audit responseAudit = objectMapper.readValue(responseContent, Audit.class);
//        assertEquals(audit1.getId(), responseAudit.getId());
//        assertEquals(audit1.getEntityType(), responseAudit.getEntityType());
//        assertEquals(audit1.getOperationType(), responseAudit.getOperationType());
//        assertEquals(audit1.getCreatedBy(), responseAudit.getCreatedBy());
//        assertEquals(audit1.getModifiedBy(), responseAudit.getModifiedBy());
////        assertEquals(audit1.getCreatedAt(), responseAudit.getCreatedAt());
////        assertEquals(audit1.getModifiedAt(), responseAudit.getModifiedAt());
//        assertEquals(audit1.getNewEntityJson(), responseAudit.getNewEntityJson());
//        assertEquals(audit1.getEntityJson(), responseAudit.getEntityJson());
//    }

    @Test
    void getAccountTransferTest() throws Exception {
        when(auditService.allAudit()).thenReturn(auditListDTO);
        mockMvc.perform(get("/audit/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}