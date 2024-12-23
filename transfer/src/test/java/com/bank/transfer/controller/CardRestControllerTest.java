package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.service.impl.CardTransferServiceImpl;
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

@WebMvcTest(CardRestController.class)
class CardRestControllerTest {
    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardTransferServiceImpl cardTransferService;

    @MockBean
    private AuditAspect auditAspect;
    ObjectMapper objectMapper = new ObjectMapper();
    CardTransferDTO cardTransferDTO1;
    CardTransferDTO cardTransferDTO2;
    CardTransfer cardTransfer1;
    CardTransfer cardTransfer2;
    List<CardTransfer> cardTransferList = new ArrayList<>();
    List<CardTransferDTO> cardTransferListDTO = new ArrayList<>();

    @BeforeEach
    public void creatNewCardTransfer() {
        cardTransfer1 = CardTransfer.builder()
                .id(1L)
                .cardNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        cardTransfer2 = CardTransfer.builder()
                .id(2L)
                .cardNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();
        cardTransferDTO1 = CardTransferDTO.builder()
                .id(1L)
                .cardNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        cardTransferDTO2 = CardTransferDTO.builder()
                .id(2L)
                .cardNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();

        cardTransferList.add(cardTransfer1);
        cardTransferList.add(cardTransfer2);

        cardTransferListDTO.add(cardTransferDTO1);
        cardTransferListDTO.add(cardTransferDTO2);
    }

    @Test
    void getCardTransferByIdTest() throws Exception {
        when(cardTransferService.getCardTransferById(ID)).thenReturn(Optional.of(cardTransferDTO1));
        String responseContent = mockMvc.perform(get("/card/{ID}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CardTransfer responseCardTransfer = objectMapper.readValue(responseContent, CardTransfer.class);
        assertEquals(cardTransfer1.getId(), responseCardTransfer.getId());
        assertEquals(cardTransfer1.getAmount(), responseCardTransfer.getAmount());
        assertEquals(cardTransfer1.getPurpose(), responseCardTransfer.getPurpose());
        assertEquals(cardTransfer1.getCardNumber(), responseCardTransfer.getCardNumber());

    }


    @Test
    void getCardTransferTest() throws Exception {
        when(cardTransferService.allCardTransfer()).thenReturn(cardTransferListDTO);
        mockMvc.perform(get("/card/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addNewCardTransferTest() throws Exception {
        when(cardTransferService.saveCardTransfer(cardTransferDTO1)).thenReturn(cardTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(cardTransfer1);
        mockMvc.perform(post("/card/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCardTransferTest() throws Exception {
        when(cardTransferService.updateCardTransferById(cardTransferDTO1, ID)).thenReturn(cardTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(cardTransfer1);
        mockMvc.perform(put("/card/{ID}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCardTransferTest() throws Exception {
        doNothing().when(cardTransferService).deleteCardTransfer(ID);
        mockMvc.perform(delete("/card/{ID}", ID))
                .andExpect(status().isNoContent());
    }
}