package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import com.bank.publicinfo.utils.TestsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_LICENSE;
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

@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService licenseService;

    private static final String licenseDtoJson = TestsUtils.toJson(TEST_LICENSE_DTO);

    private static final String listLicenseDtoJson = TestsUtils.toJson(TEST_LIST_LICENSE);

    @Test
    void getLicenseById() throws Exception {
        when(licenseService.findById(TEST_ID_1)).thenReturn(TEST_LICENSE_DTO);
        mockMvc.perform(get("/licenses/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(licenseDtoJson));
        verify(licenseService).findById(anyLong());
    }

    @Test
    void getAllLicenses() throws Exception {
        when(licenseService.findAll()).thenReturn(TEST_LIST_LICENSE);
        mockMvc.perform(get("/licenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listLicenseDtoJson));
        verify(licenseService).findAll();
    }

    @Test
    void addLicense() throws Exception {
        when(licenseService.addLicence(any(LicenseDto.class))).thenReturn(TEST_LICENSE_DTO);
        mockMvc.perform(post("/licenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(licenseDtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(licenseDtoJson));
        verify(licenseService).addLicence(any(LicenseDto.class));
    }

    @Test
    void updateLicense() throws Exception {
        when(licenseService.updateLicense(anyLong(), any(LicenseDto.class))).thenReturn(TEST_LICENSE_DTO);
        mockMvc.perform(patch("/licenses/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(licenseDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(licenseDtoJson));
        verify(licenseService).updateLicense(anyLong(), any(LicenseDto.class));
    }

    @Test
    void deleteLicense() throws Exception {
        doNothing().when(licenseService).deleteLicenceById(TEST_ID_1);
        mockMvc.perform(delete("/licenses/{id}", TEST_ID_1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(licenseService).deleteLicenceById(anyLong());
    }
}
