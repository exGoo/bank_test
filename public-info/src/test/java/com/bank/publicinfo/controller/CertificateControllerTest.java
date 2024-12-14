package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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

@WebMvcTest(CertificateController.class)
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificateService certificateService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Long TEST_ID = 1L;

    private static final CertificateDto DTO = CertificateDto.builder()
            .id(TEST_ID)
            .bankDetailsId(3L)
            .build();

    private static final List<CertificateDto> DTO_LIST = Arrays.asList(
            new CertificateDto(2L, 7L),
            new CertificateDto(3L, 11L)
    );

    @Test
    void getCertificateById() throws Exception {
        when(certificateService.findById(TEST_ID)).thenReturn(DTO);
        mockMvc.perform(get("/certificates/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankDetailsId").value(3L));
        verify(certificateService, times(1)).findById(anyLong());
    }

    @Test
    void getAllCertificates() throws Exception {
        when(certificateService.findAll()).thenReturn(DTO_LIST);
        mockMvc.perform(get("/certificates"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bankDetailsId").value(11L));
        verify(certificateService, times(1)).findAll();
    }

    @Test
    void addCertificate() throws Exception {
        when(certificateService.addCertificate(any(CertificateDto.class))).thenReturn(DTO);
        String newCertificateJson = objectMapper.writeValueAsString(DTO);
        mockMvc.perform(post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCertificateJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(certificateService, times(1)).addCertificate(any(CertificateDto.class));
    }

    @Test
    void updateCertificate() throws Exception {
        CertificateDto updatedDto = DTO.toBuilder().bankDetailsId(4L).build();
        when(certificateService.updateCertificate(anyLong(), any(CertificateDto.class))).thenReturn(updatedDto);
        String updatedAtmJson = objectMapper.writeValueAsString(updatedDto);
        mockMvc.perform(patch("/certificates/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAtmJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankDetailsId").value(updatedDto.getBankDetailsId()));
        verify(certificateService, times(1)).updateCertificate(anyLong(), any(CertificateDto.class));
    }

    @Test
    void deleteCertificate() throws Exception {
        doNothing().when(certificateService).deleteCertificateById(TEST_ID);
        mockMvc.perform(delete("/certificates/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(certificateService, times(1)).deleteCertificateById(anyLong());
    }
}
