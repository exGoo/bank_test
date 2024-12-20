package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_INVAID_JSON_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_INVALID_JSON;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_CERTIFICATES;
import static com.bank.publicinfo.utils.TestsUtils.toJson;
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

@WebMvcTest(CertificateController.class)
class CertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificateService certificateService;

    private static final String certificateDtoJson = toJson(TEST_CERTIFICATE_DTO);

    private static final String listCertificatesDtoJson = toJson(TEST_LIST_CERTIFICATES);

    @Test
    void getCertificateById() throws Exception {
        when(certificateService.findById(TEST_ID_1)).thenReturn(TEST_CERTIFICATE_DTO);
        mockMvc.perform(get("/certificates/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(certificateDtoJson));
        verify(certificateService).findById(anyLong());
    }

    @Test
    void getAllCertificates() throws Exception {
        when(certificateService.findAll()).thenReturn(TEST_LIST_CERTIFICATES);
        mockMvc.perform(get("/certificates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listCertificatesDtoJson));
        verify(certificateService).findAll();
    }

    @Test
    void getCertificateByIdWhenNotFound() throws Exception {
        when(certificateService.findById(TEST_ID_1)).thenThrow(new EntityNotFoundException("Certificate not found"));
        mockMvc.perform(get("/certificates/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Certificate not found"));
        verify(certificateService).findById(anyLong());
    }

    @Test
    void getAllCertificatesWhenNotFound() throws Exception {
        when(certificateService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/certificates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(certificateService).findAll();
    }

    @Test
    void addCertificate() throws Exception {
        when(certificateService.addCertificate(any(CertificateDto.class))).thenReturn(TEST_CERTIFICATE_DTO);
        mockMvc.perform(post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(certificateDtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(certificateDtoJson));
        verify(certificateService).addCertificate(any(CertificateDto.class));
    }

    @Test
    void addCertificateWhenRequestBodyInvalid() throws Exception {
        mockMvc.perform(post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_INVALID_JSON))
                .andExpect(status().isBadRequest());
        verify(certificateService, never()).addCertificate(any(CertificateDto.class));
    }

    @Test
    void updateCertificate() throws Exception {
        when(certificateService.updateCertificate(anyLong(), any(CertificateDto.class))).thenReturn(TEST_CERTIFICATE_DTO);
        mockMvc.perform(patch("/certificates/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(certificateDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(certificateDtoJson));
        verify(certificateService).updateCertificate(anyLong(), any(CertificateDto.class));
    }

    @Test
    void updateCertificateWhenNotFound() throws Exception {
        when(certificateService.updateCertificate(anyLong(), any(CertificateDto.class)))
                .thenThrow(new EntityNotFoundException("Certificate not found with id " + TEST_ID_1));
        mockMvc.perform(patch("/certificates/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_INVAID_JSON_2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Certificate not found with id " + TEST_ID_1));
        verify(certificateService).updateCertificate(anyLong(), any(CertificateDto.class));
    }

    @Test
    void deleteCertificate() throws Exception {
        doNothing().when(certificateService).deleteCertificateById(TEST_ID_1);
        mockMvc.perform(delete("/certificates/{id}", TEST_ID_1))
                .andExpect(status().isNoContent());
        verify(certificateService).deleteCertificateById(anyLong());
    }

    @Test
    void deleteCertificateWhenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Certificate not found with id "+ TEST_ID_1))
                .when(certificateService).deleteCertificateById(TEST_ID_1);
        mockMvc.perform(delete("/certificates/{id}", TEST_ID_1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Certificate not found with id " + TEST_ID_1));
        verify(certificateService).deleteCertificateById(anyLong());
    }
}
