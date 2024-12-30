package com.bank.transfer.controller;

import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.service.impl.CardTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.CARD_TRANSFER_URL;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.cardTransfer1;
import static com.bank.transfer.ResourcesForTests.cardTransfer2;
import static com.bank.transfer.ResourcesForTests.cardTransferDTO1;
import static com.bank.transfer.ResourcesForTests.cardTransferDTO2;
import static com.bank.transfer.ResourcesForTests.cardTransferListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardRestController.class)
class CardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardTransferServiceImpl cardTransferService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCardTransferByIdTest() throws Exception {
        when(cardTransferService.getCardTransferById(ID_2)).thenReturn(Optional.of(cardTransferDTO2));
        String responseContent = mockMvc.perform(get(CARD_TRANSFER_URL + ID_2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CardTransfer responseCardTransfer = objectMapper.readValue(responseContent, CardTransfer.class);
        assertEquals(cardTransfer2.getId(), responseCardTransfer.getId());
        assertEquals(cardTransfer2.getAmount(), responseCardTransfer.getAmount());
        assertEquals(cardTransfer2.getPurpose(), responseCardTransfer.getPurpose());
        assertEquals(cardTransfer2.getCardNumber(), responseCardTransfer.getCardNumber());

    }

    @Test
    void getCardTransferTest() throws Exception {
        when(cardTransferService.allCardTransfer()).thenReturn(cardTransferListDTO);
        mockMvc.perform(get(CARD_TRANSFER_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addNewCardTransferTest() throws Exception {
        when(cardTransferService.saveCardTransfer(cardTransferDTO1)).thenReturn(cardTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(cardTransfer1);
        mockMvc.perform(post(CARD_TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCardTransferTest() throws Exception {
        when(cardTransferService.updateCardTransferById(cardTransferDTO1, ID_1)).thenReturn(cardTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(cardTransfer1);
        mockMvc.perform(put(CARD_TRANSFER_URL + ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCardTransferTest() throws Exception {
        doNothing().when(cardTransferService).deleteCardTransfer(ID_1);
        mockMvc.perform(delete(CARD_TRANSFER_URL + ID_1))
                .andExpect(status().isNoContent());
    }
}