package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
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

@WebMvcTest(BankDetailsController.class)
class BankDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankDetailsService bankDetailsService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Long TEST_ID = 1L;

    private static final BankDetailsDto DTO = new BankDetailsDto(TEST_ID, "Moscow");

    private static final BankDetailsDto CREATED_BANK_DETAILS = BankDetailsDto.builder()
            .id(1L)
            .bik(12324L)
            .inn(12434L)
            .kpp(13241L)
            .corAccount(1232)
            .city("Moscow")
            .jointStockCompany("MyCompany")
            .name("BankAt")
            .build();

    private static final List<BankDetailsDto> DTO_LIST = Arrays.asList(
            new BankDetailsDto(2L, "Minsk"),
            new BankDetailsDto(3L, "Minsk")
    );

    @Test
    void getBankDetailsById() throws Exception {
        when(bankDetailsService.findById(TEST_ID)).thenReturn(DTO);
        mockMvc.perform(get("/bank-details/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Moscow"));
        verify(bankDetailsService, times(1)).findById(anyLong());
    }

    @Test
    void getAllBankDetails() throws Exception {
        when(bankDetailsService.findAllWithRelations()).thenReturn(DTO_LIST);
        mockMvc.perform(get("/bank-details"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("Minsk"));
        verify(bankDetailsService, times(1)).findAllWithRelations();
    }

    @Test
    void getBankDetailsByCity() throws Exception {
        when(bankDetailsService.findByCity("Minsk")).thenReturn(DTO_LIST);
        mockMvc.perform(get("/bank-details/city/{city}", "Minsk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value("Minsk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("Minsk"));
        verify(bankDetailsService, times(1)).findByCity("Minsk");
    }

    @Test
    void addBankDetails() throws Exception {
        when(bankDetailsService.addBankDetails(any(BankDetailsDto.class))).thenReturn(CREATED_BANK_DETAILS);
        String bankDetailsDTOJson = objectMapper.writeValueAsString(CREATED_BANK_DETAILS);
        mockMvc.perform(post("/bank-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bankDetailsDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CREATED_BANK_DETAILS.getId()));
        verify(bankDetailsService, times(1)).addBankDetails(any(BankDetailsDto.class));
    }

    @Test
    void updateBankDetails() throws Exception {
        BankDetailsDto updatedDto = CREATED_BANK_DETAILS.toBuilder().city("Rome").build();
        when(bankDetailsService.updateBankDetails(anyLong(), any(BankDetailsDto.class))).thenReturn(updatedDto);
        String updatedBankDetailsJson = objectMapper.writeValueAsString(updatedDto);
        mockMvc.perform(patch("/bank-details/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBankDetailsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(updatedDto.getCity()));
        verify(bankDetailsService, times(1)).updateBankDetails(anyLong(), any(BankDetailsDto.class));
    }

    @Test
    void deleteBankDetails() throws Exception {
        doNothing().when(bankDetailsService).deleteBankDetailsById(TEST_ID);
        mockMvc.perform(delete("/bank-details/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(bankDetailsService, times(1)).deleteBankDetailsById(anyLong());
    }
}
