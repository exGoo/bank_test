package com.bank.transfer.controller;

import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.service.impl.PhoneTransferServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.PHONE_TRANSFER_URL;
import static com.bank.transfer.ResourcesForTests.phoneTransfer1;
import static com.bank.transfer.ResourcesForTests.phoneTransfer2;
import static com.bank.transfer.ResourcesForTests.phoneTransferDTO1;
import static com.bank.transfer.ResourcesForTests.phoneTransferDTO2;
import static com.bank.transfer.ResourcesForTests.phoneTransferListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneRestController.class)
class PhoneRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneTransferServiceImpl phoneTransferService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getPhoneTransferByIdTest() throws Exception {
        when(phoneTransferService.getPhoneTransferById(ID_2)).thenReturn(Optional.of(phoneTransferDTO2));
        String responseContent = mockMvc.perform(get(PHONE_TRANSFER_URL + ID_2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PhoneTransfer responsePhoneTransfer = objectMapper.readValue(responseContent, PhoneTransfer.class);
        assertEquals(phoneTransfer2.getId(), responsePhoneTransfer.getId());
        assertEquals(phoneTransfer2.getAmount(), responsePhoneTransfer.getAmount());
        assertEquals(phoneTransfer2.getPurpose(), responsePhoneTransfer.getPurpose());
        assertEquals(phoneTransfer2.getPhoneNumber(), responsePhoneTransfer.getPhoneNumber());

    }

    @Test
    void getPhoneTransferTest() throws Exception {
        when(phoneTransferService.allPhoneTransfer()).thenReturn(phoneTransferListDTO);
        mockMvc.perform(get(PHONE_TRANSFER_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addNewPhoneTransferTest() throws Exception {
        when(phoneTransferService.savePhoneTransfer(phoneTransferDTO1)).thenReturn(phoneTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(phoneTransfer1);
        mockMvc.perform(post(PHONE_TRANSFER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePhoneTransferTest() throws Exception {
        when(phoneTransferService.updatePhoneTransferById(phoneTransferDTO1, ID_1)).thenReturn(phoneTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(phoneTransfer1);
        mockMvc.perform(put(PHONE_TRANSFER_URL + ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deletePhoneTransferTest() throws Exception {
        doNothing().when(phoneTransferService).deletePhoneTransfer(ID_1);
        mockMvc.perform(delete(PHONE_TRANSFER_URL + ID_1))
                .andExpect(status().isNoContent());
    }
}
