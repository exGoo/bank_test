package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.service.impl.AccountTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountRestController.class)
class AccountRestControllerTest {
    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTransferServiceImpl accountTransferService;

    @MockBean
    private AuditAspect auditAspect;
    ObjectMapper objectMapper = new ObjectMapper();
    AccountTransferDTO accountTransferDTO1;
    AccountTransferDTO accountTransferDTO2;
    AccountTransfer accountTransfer1;
    AccountTransfer accountTransfer2;
    List<AccountTransfer> accountTransferList = new ArrayList<>();
    List<AccountTransferDTO> accountTransferListDTO = new ArrayList<>();

    @BeforeEach
    public void creatNewAccountTransfer() {
        accountTransfer1 = AccountTransfer.builder()
                .id(1L)
                .accountNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        accountTransfer2 = AccountTransfer.builder()
                .id(2L)
                .accountNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();
        accountTransferDTO1 = AccountTransferDTO.builder()
                .id(1L)
                .accountNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        accountTransferDTO2 = AccountTransferDTO.builder()
                .id(2L)
                .accountNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();

        accountTransferList.add(accountTransfer1);
        accountTransferList.add(accountTransfer2);

        accountTransferListDTO.add(accountTransferDTO1);
        accountTransferListDTO.add(accountTransferDTO2);
    }

    @Test
    void getAccountTransferByIdTest() throws Exception {
        when(accountTransferService.getAccountTransferById(ID)).thenReturn(Optional.of(accountTransferDTO1));
        String responseContent = mockMvc.perform(get("/account/{ID}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AccountTransfer responseAccountTransfer = objectMapper.readValue(responseContent, AccountTransfer.class);
        assertEquals(accountTransfer1.getId(), responseAccountTransfer.getId());
        assertEquals(accountTransfer1.getAmount(), responseAccountTransfer.getAmount());
        assertEquals(accountTransfer1.getPurpose(), responseAccountTransfer.getPurpose());
        assertEquals(accountTransfer1.getAccountNumber(), responseAccountTransfer.getAccountNumber());

    }


    @Test
    void getAccountTransferTest() throws Exception {
        when(accountTransferService.allAccountTransfer()).thenReturn(accountTransferListDTO);
        mockMvc.perform(get("/account/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addNewAccountTransferTest() throws Exception {
        when(accountTransferService.saveAccountTransfer(accountTransferDTO1)).thenReturn(accountTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(accountTransfer1);
        mockMvc.perform(post("/account/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void updateAccountTransferTest() throws Exception {
        when(accountTransferService.updateAccountTransferById(accountTransferDTO1, ID)).thenReturn(accountTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(accountTransfer1);
        mockMvc.perform(put("/account/{ID}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAccountTransferTest() throws Exception {
        doNothing().when(accountTransferService).deleteAccountTransfer(ID);
        mockMvc.perform(delete("/account/{ID}", ID))
                .andExpect(status().isNoContent());
    }
}
