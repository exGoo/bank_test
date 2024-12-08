package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.repository.LicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LicenseServiceImplTest {

    @InjectMocks
    private LicenseServiceImpl licenseService;

    @Mock
    private LicenseRepository licenseRepository;

    @Mock
    private LicenseMapper licenseMapper;

    private License license;

    private LicenseDto licenseDto;

    @BeforeEach
    public void setUp() {
        license = new License();
        license.setId(1L);
        licenseDto = new LicenseDto();
        licenseDto.setId(1L);
    }

    @Test
    void findByIdSucces() {
        when(licenseRepository.findById(1L)).thenReturn(Optional.of(license));
        when(licenseMapper.toDto(license)).thenReturn(licenseDto);
        LicenseDto result = licenseService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(licenseRepository, times(1)).findById(1L);
        verify(licenseMapper, times(1)).toDto(license);
    }

    @Test
    void findByIdFailure() {
        Long id = 1L;
        when(licenseRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            licenseService.findById(id);
        });
        assertEquals("License not found with id " + id, exception.getMessage());
        verify(licenseRepository).findById(id);
    }

    @Test
    void findAll() {
        List<License> licenseList = List.of(license);
        when(licenseRepository.findAll()).thenReturn(licenseList);
        when(licenseMapper.toDto(license)).thenReturn(licenseDto);
        List<LicenseDto> result = licenseService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(licenseRepository, times(1)).findAll();
        verify(licenseMapper, times(1)).toDto(license);
    }

    @Test
    void addLicense() {
        when(licenseMapper.toModel(licenseDto)).thenReturn(license);
        when(licenseRepository.save(license)).thenReturn(license);
        when(licenseMapper.toDto(license)).thenReturn(licenseDto);
        LicenseDto result = licenseService.addLicence(licenseDto);
        assertNotNull(result);
        verify(licenseRepository, times(1)).save(license);
        verify(licenseMapper, times(1)).toModel(licenseDto);
        verify(licenseMapper, times(1)).toDto(license);
    }

    @Test
    void deleteLicenseByIdSucces() {
        doNothing().when(licenseRepository).deleteById(1L);
        licenseService.deleteLicenceById(1L);
        verify(licenseRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLicenseByIdFailure() {
        doThrow(new EntityNotFoundException("License not found with id 1")).when(licenseRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> licenseService.deleteLicenceById(1L));
        verify(licenseRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateLicenseSucces() {
        when(licenseRepository.findById(1L)).thenReturn(Optional.of(license));
        when(licenseRepository.save(any(License.class))).thenReturn(license);
        when(licenseMapper.toDto(license)).thenReturn(licenseDto);
        LicenseDto result = licenseService.updateLicense(1L, licenseDto);
        assertNotNull(result);
        verify(licenseRepository, times(1)).findById(1L);
        verify(licenseRepository, times(1)).save(any(License.class));
        verify(licenseMapper, times(1)).toDto(license);
    }

    @Test
    void updateLicenseFailure() {
        when(licenseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> licenseService.updateLicense(1L, licenseDto));
        verify(licenseRepository, times(1)).findById(1L);
    }
}