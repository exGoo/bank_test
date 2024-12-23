package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
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
import static com.bank.antifraud.TestsRecourse.Dto;
import static com.bank.antifraud.TestsRecourse.ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuspiciousPhoneTransfersController.class)
@DisplayName("SuspiciousPhoneTransfer controller test")
class SuspiciousPhoneTransfersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SuspiciousPhoneTransfersService sptService;

    private static final SuspiciousPhoneTransfersDto DTO = Dto.DEFAULT.getSptDto();

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getPageSuspiciousPhoneTransfersDto")
    void getAllShouldReturnPageOfTransfers(Page<SuspiciousPhoneTransfersDto> page) throws Exception {
        if (page.isEmpty()) {
            when(sptService.getAll(any(PageRequest.class))).thenReturn(page);
            mockMvc.perform(get("/spt")
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isEmpty());
        } else {
            when(sptService.getAll(any(PageRequest.class))).thenReturn(page);

            mockMvc.perform(get("/spt")
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content[0].id").value(ID));
        }
    }

    @Test
    void getShouldReturnTransferById() throws Exception {
        when(sptService.get(anyLong())).thenReturn(DTO);

        mockMvc.perform(get("/spt/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void saveShouldCreateNewTransfer() throws Exception {
        when(sptService.add(any(SuspiciousPhoneTransfersDto.class))).thenReturn(DTO);

        mockMvc.perform(post("/spt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.isSuspicious").value(true));
    }

    @Test
    void updateShouldModifyExistingTransfer() throws Exception {
        when(sptService.update(anyLong(), any(SuspiciousPhoneTransfersDto.class)))
                .thenReturn(Dto.UPDATED_DTO.getSptDto());

        mockMvc.perform(patch("/spt/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Dto.UPDATED_DTO.getSptDto())))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Update success. Suspicious phone transfer with id " + ID + " was updated."));
    }

    @Test
    void deleteShouldRemoveTransfer() throws Exception {
        doNothing().when(sptService).remove(ID);

        mockMvc.perform(delete("/spt/" + ID))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Delete success. Suspicious phone transfer with id " + ID + " was deleted."));
    }
}
