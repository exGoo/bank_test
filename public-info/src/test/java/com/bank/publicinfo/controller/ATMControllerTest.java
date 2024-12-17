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
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_ATMS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    void deleteAtm() throws Exception {
        doNothing().when(atmService).deleteATMById(TEST_ID_1);
        mockMvc.perform(delete("/atms/{id}", TEST_ID_1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(atmService).deleteATMById(anyLong());
    }
}
