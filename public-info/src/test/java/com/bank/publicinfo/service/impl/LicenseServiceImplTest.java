package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.LicenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_DTO_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_LICENSE;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_LICENSE_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LicenseServiceImplTest {

    @InjectMocks
    private LicenseServiceImpl licenseService;

    @Mock
    private LicenseRepository licenseRepository;

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private LicenseMapper licenseMapper;

    @Test
    void findByIdSuccess() {
        when(licenseRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_LICENSE_1));
        when(licenseMapper.toDto(TEST_LICENSE_1)).thenReturn(TEST_LICENSE_DTO);
        LicenseDto result = licenseService.findById(TEST_ID_1);
        assertNotNull(result);
        assertEquals(TEST_LICENSE_DTO,result);
        verify(licenseRepository).findById(TEST_ID_1);
        verify(licenseMapper).toDto(TEST_LICENSE_1);
    }

    @Test
    void findByIdFailure() {
        when(licenseRepository.findById(TEST_ID_2)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            licenseService.findById(TEST_ID_2);
        });
        assertTrue(exception.getMessage().contains("License not found with id " + TEST_ID_2));
        verify(licenseRepository).findById(TEST_ID_2);
    }

    @Test
    void findAll() {
        when(licenseRepository.findAll()).thenReturn(TEST_LIST_LICENSE_2);
        when(licenseMapper.toDto(TEST_LICENSE_1)).thenReturn(TEST_LICENSE_DTO);
        when(licenseMapper.toDto(TEST_LICENSE_2)).thenReturn(TEST_LICENSE_DTO_2);
        List<LicenseDto> result = licenseService.findAll();
        assertNotNull(result);
        assertEquals(TEST_LIST_LICENSE, result);
        verify(licenseRepository).findAll();
        verify(licenseMapper).toDto(TEST_LICENSE_1);
        verify(licenseMapper).toDto(TEST_LICENSE_2);
    }

    @Test
    void findAllElseEmptyList() {
        when(licenseRepository.findAll()).thenReturn(Collections.emptyList());
        List<LicenseDto> result = licenseService.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void addLicense() {
        when(licenseMapper.toModel(TEST_LICENSE_DTO)).thenReturn(TEST_LICENSE_1);
        when(licenseRepository.save(TEST_LICENSE_1)).thenReturn(TEST_LICENSE_1);
        when(licenseMapper.toDto(TEST_LICENSE_1)).thenReturn(TEST_LICENSE_DTO);
        LicenseDto result = licenseService.addLicence(TEST_LICENSE_DTO);
        assertNotNull(result);
        verify(licenseRepository).save(TEST_LICENSE_1);
        verify(licenseMapper).toModel(TEST_LICENSE_DTO);
        verify(licenseMapper).toDto(TEST_LICENSE_1);
    }

    @Test
    void deleteLicenseByIdSuccess() {
        doNothing().when(licenseRepository).deleteById(TEST_ID_1);
        licenseService.deleteLicenceById(TEST_ID_1);
        verify(licenseRepository).deleteById(TEST_ID_1);
    }

    @Test
    void deleteLicenseByIdFailure() {
        doThrow(new EntityNotFoundException("License not found with id " + TEST_ID_1)).when(licenseRepository).deleteById(TEST_ID_1);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.deleteLicenceById(TEST_ID_1));
        assertTrue(exception.getMessage().contains("License not found with id " + TEST_ID_1));
        verify(licenseRepository).deleteById(TEST_ID_1);
    }

    @Test
    void updateLicenseSuccess() {
        when(licenseRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_LICENSE_1));
        when(licenseRepository.save(any(License.class))).thenReturn(TEST_LICENSE_1);
        when(licenseMapper.toDto(TEST_LICENSE_1)).thenReturn(TEST_LICENSE_DTO);
        when(bankDetailsRepository.findById(TEST_LICENSE_DTO.getBankDetailsId()))
                .thenReturn(Optional.of(TEST_LICENSE_1.getBankDetails()));
        LicenseDto result = licenseService.updateLicense(TEST_ID_1, TEST_LICENSE_DTO);
        assertNotNull(result);
        verify(licenseRepository).findById(TEST_ID_1);
        verify(licenseRepository).save(any(License.class));
        verify(licenseMapper).toDto(TEST_LICENSE_1);
        verify(bankDetailsRepository).findById(TEST_LICENSE_DTO.getBankDetailsId());
    }

    @Test
    void updateLicenseFailure() {
        when(licenseRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> licenseService.updateLicense(TEST_ID_1, TEST_LICENSE_DTO));
        assertTrue(exception.getMessage().contains("License not found with id " + TEST_ID_1));
        verify(licenseRepository).findById(TEST_ID_1);
    }
}
