package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.service.impl.PhoneTransferServiceImpl;
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

@WebMvcTest(PhoneRestController.class)
class PhoneRestControllerTest {
    private static final Long ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneTransferServiceImpl phoneTransferService;

    @MockBean
    private AuditAspect auditAspect;
    ObjectMapper objectMapper = new ObjectMapper();
    PhoneTransferDTO phoneTransferDTO1;
    PhoneTransferDTO phoneTransferDTO2;
    PhoneTransfer phoneTransfer1;
    PhoneTransfer phoneTransfer2;
    List<PhoneTransfer> phoneTransferList = new ArrayList<>();
    List<PhoneTransferDTO> phoneTransferListDTO = new ArrayList<>();

    @BeforeEach
    public void creatNewPhoneTransfer() {
        phoneTransfer1 = PhoneTransfer.builder()
                .id(1L)
                .phoneNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        phoneTransfer2 = PhoneTransfer.builder()
                .id(2L)
                .phoneNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();
        phoneTransferDTO1 = PhoneTransferDTO.builder()
                .id(1L)
                .phoneNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        phoneTransferDTO2 = PhoneTransferDTO.builder()
                .id(2L)
                .phoneNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();

        phoneTransferList.add(phoneTransfer1);
        phoneTransferList.add(phoneTransfer2);

        phoneTransferListDTO.add(phoneTransferDTO1);
        phoneTransferListDTO.add(phoneTransferDTO2);
    }

    @Test
    void getPhoneTransferByIdTest() throws Exception {
        when(phoneTransferService.getPhoneTransferById(ID)).thenReturn(Optional.of(phoneTransferDTO1));
        String responseContent = mockMvc.perform(get("/phone/{ID}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PhoneTransfer responsePhoneTransfer = objectMapper.readValue(responseContent, PhoneTransfer.class);
        assertEquals(phoneTransfer1.getId(), responsePhoneTransfer.getId());
        assertEquals(phoneTransfer1.getAmount(), responsePhoneTransfer.getAmount());
        assertEquals(phoneTransfer1.getPurpose(), responsePhoneTransfer.getPurpose());
        assertEquals(phoneTransfer1.getPhoneNumber(), responsePhoneTransfer.getPhoneNumber());

    }


    @Test
    void getPhoneTransferTest() throws Exception {
        when(phoneTransferService.allPhoneTransfer()).thenReturn(phoneTransferListDTO);
        mockMvc.perform(get("/phone/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addNewPhoneTransferTest() throws Exception {
        when(phoneTransferService.savePhoneTransfer(phoneTransferDTO1)).thenReturn(phoneTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(phoneTransfer1);
        mockMvc.perform(post("/phone/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePhoneTransferTest() throws Exception {
        when(phoneTransferService.updatePhoneTransferById(phoneTransferDTO1, ID)).thenReturn(phoneTransfer1);
        String jsonRequest = objectMapper.writeValueAsString(phoneTransfer1);
        mockMvc.perform(put("/phone/{ID}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void deletePhoneTransferTest() throws Exception {
        doNothing().when(phoneTransferService).deletePhoneTransfer(ID);
        mockMvc.perform(delete("/phone/{ID}", ID))
                .andExpect(status().isNoContent());
    }
}
