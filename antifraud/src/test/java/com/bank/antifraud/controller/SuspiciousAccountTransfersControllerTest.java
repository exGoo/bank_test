package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static com.bank.antifraud.TestsRecourse.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SuspiciousAccountTransfersController.class)
@DisplayName("SuspiciousAccountTransfer controller test")
class SuspiciousAccountTransfersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SuspiciousAccountTransfersService satService;

    private static final SuspiciousAccountTransfersDto DTO = Dto.DEFAULT.getSatDto();

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getPageSuspiciousAccountTransfersDto")
    void getAllShouldReturnPageOfTransfers(Page<SuspiciousAccountTransfersDto> page) throws Exception {
        if (page.isEmpty()) {
            when(satService.getAll(any(PageRequest.class))).thenReturn(page);
            mockMvc.perform(get("/sat")
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isEmpty());
        } else {
            when(satService.getAll(any(PageRequest.class))).thenReturn(page);

            mockMvc.perform(get("/sat")
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content[0].id").value(ID));
        }
    }

    @Test
    void getShouldReturnTransferById() throws Exception {
        when(satService.get(anyLong())).thenReturn(DTO);

        mockMvc.perform(get("/sat/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void saveShouldCreateNewTransfer() throws Exception {
        when(satService.add(any(SuspiciousAccountTransfersDto.class))).thenReturn(DTO);

        mockMvc.perform(post("/sat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.isSuspicious").value(true));
    }

    @Test
    void updateShouldModifyExistingTransfer() throws Exception {
        when(satService.update(anyLong(), any(SuspiciousAccountTransfersDto.class)))
                .thenReturn(Dto.UPDATED_DTO.getSatDto());

        mockMvc.perform(patch("/sat/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Dto.UPDATED_DTO.getSatDto())))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Update success. Suspicious account transfer with id " + ID + " was updated."));
    }

    @Test
    void deleteShouldRemoveTransfer() throws Exception {
        doNothing().when(satService).remove(ID);

        mockMvc.perform(delete("/sat/" + ID))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Delete success. Suspicious account transfer with id " + ID + " was deleted."));
    }
}
