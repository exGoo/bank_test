package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.CertificateRepository;
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
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    private Certificate certificate;

    private CertificateDto certificateDto;

    @BeforeEach
    public void setUp() {
        certificate = new Certificate();
        certificate.setId(1L);
        certificateDto = new CertificateDto();
        certificateDto.setId(1L);
    }

    @Test
    void findByIdSucces() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateMapper.toDto(certificate)).thenReturn(certificateDto);
        CertificateDto result = certificateService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(certificateRepository, times(1)).findById(1L);
        verify(certificateMapper, times(1)).toDto(certificate);
    }

    @Test
    void findByIdFailure() {
        Long id = 1L;
        when(certificateRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            certificateService.findById(id);
        });
        assertEquals("Certificate not found with id " + id, exception.getMessage());
        verify(certificateRepository).findById(id);
    }

    @Test
    void findAll() {
        List<Certificate> atmList = List.of(certificate);
        when(certificateRepository.findAll()).thenReturn(atmList);
        when(certificateMapper.toDto(certificate)).thenReturn(certificateDto);
        List<CertificateDto> result = certificateService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(certificateRepository, times(1)).findAll();
        verify(certificateMapper, times(1)).toDto(certificate);
    }

    @Test
    void addCertificate() {
        when(certificateMapper.toModel(certificateDto)).thenReturn(certificate);
        when(certificateRepository.save(certificate)).thenReturn(certificate);
        when(certificateMapper.toDto(certificate)).thenReturn(certificateDto);
        CertificateDto result = certificateService.addCertificate(certificateDto);
        assertNotNull(result);
        verify(certificateRepository, times(1)).save(certificate);
        verify(certificateMapper, times(1)).toModel(certificateDto);
        verify(certificateMapper, times(1)).toDto(certificate);
    }

    @Test
    void deleteCertificateByIdSucces() {
        doNothing().when(certificateRepository).deleteById(1L);
        certificateService.deleteCertificateById(1L);
        verify(certificateRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCertificateByIdFailure() {
        doThrow(new EntityNotFoundException("Certificate not found with id 1")).when(certificateRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> certificateService.deleteCertificateById(1L));
        verify(certificateRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateCertificateSucces() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(certificate);
        when(certificateMapper.toDto(certificate)).thenReturn(certificateDto);
        CertificateDto result = certificateService.updateCertificate(1L, certificateDto);
        assertNotNull(result);
        verify(certificateRepository, times(1)).findById(1L);
        verify(certificateRepository, times(1)).save(any(Certificate.class));
        verify(certificateMapper, times(1)).toDto(certificate);
    }

    @Test
    void updateCertificateFailure() {
        when(certificateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> certificateService.updateCertificate(1L, certificateDto));
        verify(certificateRepository, times(1)).findById(1L);
    }
}
