package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
import com.bank.publicinfo.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_DETAILS_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_DETAILS;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankDetailsController.class)
class BankDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankDetailsService bankDetailsService;

    private static final String bankDetailsDtoJson = TestsUtils.toJson(TEST_DETAILS_DTO);

    private static final String listBankDetailsDtoJson = TestsUtils.toJson(TEST_LIST_DETAILS);

    @Test
    void getBankDetailsById() throws Exception {
        when(bankDetailsService.findById(TEST_ID_1)).thenReturn(TEST_DETAILS_DTO);
        mockMvc.perform(get("/bank-details/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(bankDetailsDtoJson));
        verify(bankDetailsService).findById(anyLong());
    }

    @Test
    void getAllBankDetails() throws Exception {
        when(bankDetailsService.findAllWithRelations()).thenReturn(TEST_LIST_DETAILS);
        mockMvc.perform(get("/bank-details").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listBankDetailsDtoJson));
        verify(bankDetailsService).findAllWithRelations();
    }

    @Test
    void getBankDetailsByCity() throws Exception {
        when(bankDetailsService.findByCity(TEST_CITY_1)).thenReturn(TEST_LIST_DETAILS);
        mockMvc.perform(get("/bank-details/city/{city}", TEST_CITY_1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value(TEST_CITY_1))
                .andExpect(jsonPath("$[1].city").value(TEST_CITY_1));
        verify(bankDetailsService).findByCity(TEST_CITY_1);
    }

    @Test
    void addBankDetails() throws Exception {
        when(bankDetailsService.addBankDetails(any(BankDetailsDto.class))).thenReturn(TEST_DETAILS_DTO);
        mockMvc.perform(post("/bank-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bankDetailsDtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(bankDetailsDtoJson));
        verify(bankDetailsService).addBankDetails(any(BankDetailsDto.class));
    }

    @Test
    void updateBankDetails() throws Exception {
        when(bankDetailsService.updateBankDetails(anyLong(), any(BankDetailsDto.class))).thenReturn(TEST_DETAILS_DTO);
        mockMvc.perform(patch("/bank-details/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bankDetailsDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(bankDetailsDtoJson));
        verify(bankDetailsService).updateBankDetails(anyLong(), any(BankDetailsDto.class));
    }

    @Test
    void deleteBankDetails() throws Exception {
        doNothing().when(bankDetailsService).deleteBankDetailsById(TEST_ID_1);
        mockMvc.perform(delete("/bank-details/{id}", TEST_ID_1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(bankDetailsService).deleteBankDetailsById(anyLong());
    }
}
