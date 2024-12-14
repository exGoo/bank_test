package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.service.ATMService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ATMController.class)
class ATMControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ATMService atmService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Long TEST_ID = 1L;

    private static final ATMDto DTO = ATMDto.builder()
            .id(TEST_ID)
            .address("UnionStreet")
            .allHours(false)
            .build();

    private static final List<ATMDto> DTO_LIST = Arrays.asList(
            new ATMDto(2L, "MyAddress"),
            new ATMDto(3L, "YourAddress")
    );

    @Test
    void getAtmById() throws Exception {
        when(atmService.findById(TEST_ID)).thenReturn(DTO);
        mockMvc.perform(get("/atms/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("UnionStreet"));
        verify(atmService, times(1)).findById(anyLong());
    }

    @Test
    void getAllAtms() throws Exception {
        when(atmService.findAll()).thenReturn(DTO_LIST);
        mockMvc.perform(get("/atms"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address").value("YourAddress"));
        verify(atmService, times(1)).findAll();
    }

    @Test
    void addAtm() throws Exception {
        when(atmService.addATM(any(ATMDto.class))).thenReturn(DTO);
        String newAtmJson = objectMapper.writeValueAsString(DTO);
        mockMvc.perform(post("/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAtmJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(atmService, times(1)).addATM(any(ATMDto.class));
    }

    @Test
    void updateAtm() throws Exception {
        ATMDto updatedDto = DTO.toBuilder().address("MyStreet").build();
        when(atmService.updateATM(anyLong(), any(ATMDto.class))).thenReturn(updatedDto);
        String updatedAtmJson = objectMapper.writeValueAsString(updatedDto);
        mockMvc.perform(patch("/atms/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAtmJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(updatedDto.getAddress()));
        verify(atmService, times(1)).updateATM(anyLong(), any(ATMDto.class));
    }

    @Test
    void deleteAtm() throws Exception {
        doNothing().when(atmService).deleteATMById(TEST_ID);
        mockMvc.perform(delete("/atms/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(atmService, times(1)).deleteATMById(anyLong());
    }
}
