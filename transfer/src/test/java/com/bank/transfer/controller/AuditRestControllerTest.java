package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.service.impl.AuditServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.bank.transfer.ResourcesForTests.AUDIT_URL;
import static com.bank.transfer.ResourcesForTests.auditListDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditRestController.class)
class AuditRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditServiceImpl auditService;

    @MockBean
    private AuditAspect auditAspect;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAccountTransferTest() throws Exception {
        when(auditService.allAudit()).thenReturn(auditListDTO);
        mockMvc.perform(get(AUDIT_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
