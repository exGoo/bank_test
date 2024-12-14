package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.CertificateService;
import com.bank.publicinfo.service.LicenseService;
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

@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService licenseService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Long TEST_ID = 1L;

    private static final LicenseDto DTO = LicenseDto.builder()
            .id(TEST_ID)
            .bankDetailsId(3L)
            .build();

    private static final List<LicenseDto> DTO_LIST = Arrays.asList(
            new LicenseDto(2L, 7L),
            new LicenseDto(3L, 11L)
    );

    @Test
    void getLicenseById() throws Exception {
        when(licenseService.findById(TEST_ID)).thenReturn(DTO);
        mockMvc.perform(get("/licenses/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankDetailsId").value(3L));
        verify(licenseService, times(1)).findById(anyLong());
    }

    @Test
    void getAllLicenses() throws Exception {
        when(licenseService.findAll()).thenReturn(DTO_LIST);
        mockMvc.perform(get("/licenses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bankDetailsId").value(11L));
        verify(licenseService, times(1)).findAll();
    }

    @Test
    void addLicense() throws Exception {
        when(licenseService.addLicence(any(LicenseDto.class))).thenReturn(DTO);
        String newLicenseJson = objectMapper.writeValueAsString(DTO);
        mockMvc.perform(post("/licenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newLicenseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(licenseService, times(1)).addLicence(any(LicenseDto.class));
    }

    @Test
    void updateLicense() throws Exception {
        LicenseDto updatedDto = DTO.toBuilder().bankDetailsId(4L).build();
        when(licenseService.updateLicense(anyLong(), any(LicenseDto.class))).thenReturn(updatedDto);
        String updatedAtmJson = objectMapper.writeValueAsString(updatedDto);
        mockMvc.perform(patch("/licenses/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAtmJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankDetailsId").value(updatedDto.getBankDetailsId()));
        verify(licenseService, times(1)).updateLicense(anyLong(), any(LicenseDto.class));
    }

    @Test
    void deleteLicense() throws Exception {
        doNothing().when(licenseService).deleteLicenceById(TEST_ID);
        mockMvc.perform(delete("/licenses/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(licenseService, times(1)).deleteLicenceById(anyLong());
    }
}
