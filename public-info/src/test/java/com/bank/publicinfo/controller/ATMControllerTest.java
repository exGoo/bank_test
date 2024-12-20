package com.bank.publicinfo.controller;

import com.bank.publicinfo.utils.TestsUtils;
import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.service.ATMService;;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_INVAID_JSON_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_INVALID_JSON;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_ATMS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ATMController.class)
class ATMControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ATMService atmService;

    private static final String atmDtoJson = TestsUtils.toJson(TEST_ATM_DTO);

    private static final String listAtmDtoJson = TestsUtils.toJson(TEST_LIST_ATMS);

    @Test
    void getAtmById() throws Exception {
        when(atmService.findById(TEST_ID_1)).thenReturn(TEST_ATM_DTO);
        mockMvc.perform(get("/atms/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(atmDtoJson));
        verify(atmService).findById(anyLong());
    }

    @Test
    void getAllAtms() throws Exception {
        when(atmService.findAll()).thenReturn(TEST_LIST_ATMS);
        mockMvc.perform(get("/atms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listAtmDtoJson));
        verify(atmService).findAll();
    }

    @Test
    void getAtmByIdWhenNotFound() throws Exception {
        when(atmService.findById(TEST_ID_1)).thenThrow(new EntityNotFoundException("ATM not found"));
        mockMvc.perform(get("/atms/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ATM not found"));
        verify(atmService).findById(anyLong());
    }

    @Test
    void getAllAtmsWhenNoAtmsFound() throws Exception {
        when(atmService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/atms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(atmService).findAll();
    }

    @Test
    void addAtm() throws Exception {
        when(atmService.addATM(any(ATMDto.class))).thenReturn(TEST_ATM_DTO);
        mockMvc.perform(post("/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmDtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(atmDtoJson));
        verify(atmService).addATM(any(ATMDto.class));
    }

    @Test
    void addAtmWhenRequestBodyInvalid() throws Exception {
        mockMvc.perform(post("/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_INVALID_JSON))
                .andExpect(status().isBadRequest());
        verify(atmService, never()).addATM(any(ATMDto.class));
    }

    @Test
    void updateAtm() throws Exception {
        when(atmService.updateATM(anyLong(), any(ATMDto.class))).thenReturn(TEST_ATM_DTO);
        mockMvc.perform(patch("/atms/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(atmDtoJson));
        verify(atmService).updateATM(anyLong(), any(ATMDto.class));
    }

    @Test
    void updateAtmWhenNotFound() throws Exception {
        when(atmService.updateATM(anyLong(), any(ATMDto.class)))
                .thenThrow(new EntityNotFoundException("ATM not found with id " + TEST_ID_1));
        mockMvc.perform(patch("/atms/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_INVAID_JSON_2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ATM not found with id " + TEST_ID_1));
        verify(atmService).updateATM(anyLong(), any(ATMDto.class));
    }

    @Test
    void deleteAtm() throws Exception {
        doNothing().when(atmService).deleteATMById(TEST_ID_1);
        mockMvc.perform(delete("/atms/{id}", TEST_ID_1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(atmService).deleteATMById(anyLong());
    }

    @Test
    void deleteAtmWhenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("ATM not found with id "+ TEST_ID_1)).when(atmService).deleteATMById(TEST_ID_1);
        mockMvc.perform(delete("/atms/{id}", TEST_ID_1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ATM not found with id " + TEST_ID_1));
        verify(atmService).deleteATMById(anyLong());
    }
}
