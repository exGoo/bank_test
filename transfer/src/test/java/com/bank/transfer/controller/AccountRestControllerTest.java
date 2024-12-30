package com.bank.transfer.controller;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.service.impl.AccountTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_TRANSFER_URL;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.accountTransfer1;
import static com.bank.transfer.ResourcesForTests.accountTransferDTO1;
import static com.bank.transfer.ResourcesForTests.accountTransferDTO2;
import static com.bank.transfer.ResourcesForTests.accountTransferListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountRestController.class)
class AccountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTransferServiceImpl accountTransferService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAccountTransferByIdTest() throws Exception {
        when(accountTransferService.getAccountTransferById(ID_2)).thenReturn(Optional.of(accountTransferDTO2));
        String responseContent = mockMvc.perform(get(ACCOUNT_TRANSFER_URL + ID_2)
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
        mockMvc.perform(get(ACCOUNT_TRANSFER_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addNewAccountTransferTest() throws Exception {
        when(accountTransferService.saveAccountTransfer(accountTransferDTO1)).thenReturn(accountTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(accountTransfer1);
        mockMvc.perform(post(ACCOUNT_TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void updateAccountTransferTest() throws Exception {
        when(accountTransferService.updateAccountTransferById(accountTransferDTO1, ID_1)).thenReturn(accountTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(accountTransfer1);
        mockMvc.perform(put(ACCOUNT_TRANSFER_URL + ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAccountTransferTest() throws Exception {
        doNothing().when(accountTransferService).deleteAccountTransfer(ID_1);
        mockMvc.perform(delete(ACCOUNT_TRANSFER_URL + ID_1))
                .andExpect(status().isNoContent());
    }
}
