package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.repository.CertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_DTO_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_CERTIFICATES;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_CERTIFICATE_2;
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
class  CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Test
    void findByIdSuccess() {
        when(certificateRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_CERTIFICATE_1));
        when(certificateMapper.toDto(TEST_CERTIFICATE_1)).thenReturn(TEST_CERTIFICATE_DTO);
        CertificateDto result = certificateService.findById(TEST_ID_1);
        assertNotNull(result);
        assertEquals(TEST_CERTIFICATE_DTO, result);
        verify(certificateRepository).findById(TEST_ID_1);
        verify(certificateMapper).toDto(TEST_CERTIFICATE_1);
    }

    @Test
    void findByIdFailure() {
        when(certificateRepository.findById(TEST_ID_2)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            certificateService.findById(TEST_ID_2);
        });
        assertTrue(exception.getMessage().contains("Certificate not found with id " + TEST_ID_2));
        verify(certificateRepository).findById(TEST_ID_2);
    }

    @Test
    void findAll() {
        when(certificateRepository.findAll()).thenReturn(TEST_LIST_CERTIFICATE_2);
        when(certificateMapper.toDto(TEST_CERTIFICATE_1)).thenReturn(TEST_CERTIFICATE_DTO);
        when(certificateMapper.toDto(TEST_CERTIFICATE_2)).thenReturn(TEST_CERTIFICATE_DTO_2);
        List<CertificateDto> result = certificateService.findAll();
        assertNotNull(result);
        assertEquals(TEST_LIST_CERTIFICATES, result);
        verify(certificateRepository).findAll();
        verify(certificateMapper).toDto(TEST_CERTIFICATE_1);
        verify(certificateMapper).toDto(TEST_CERTIFICATE_2);
    }

    @Test
    void findAllElseEmptyList() {
        when(certificateRepository.findAll()).thenReturn(Collections.emptyList());
        List<CertificateDto> result = certificateService.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void addCertificate() {
        when(certificateMapper.toModel(TEST_CERTIFICATE_DTO)).thenReturn(TEST_CERTIFICATE_1);
        when(certificateRepository.save(TEST_CERTIFICATE_1)).thenReturn(TEST_CERTIFICATE_1);
        when(certificateMapper.toDto(TEST_CERTIFICATE_1)).thenReturn(TEST_CERTIFICATE_DTO);
        CertificateDto result = certificateService.addCertificate(TEST_CERTIFICATE_DTO);
        assertNotNull(result);
        verify(certificateRepository).save(TEST_CERTIFICATE_1);
        verify(certificateMapper).toModel(TEST_CERTIFICATE_DTO);
        verify(certificateMapper).toDto(TEST_CERTIFICATE_1);
    }

    @Test
    void deleteCertificateByIdSuccess() {
        doNothing().when(certificateRepository).deleteById(TEST_ID_1);
        certificateService.deleteCertificateById(TEST_ID_1);
        verify(certificateRepository).deleteById(TEST_ID_1);
    }

    @Test
    void deleteCertificateByIdFailure() {
        doThrow(new EntityNotFoundException("Certificate not found with id " + TEST_ID_2))
                .when(certificateRepository).deleteById(TEST_ID_2);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> certificateService.deleteCertificateById(TEST_ID_2));
        assertTrue(exception.getMessage().contains("Certificate not found with id " + TEST_ID_2));
        verify(certificateRepository).deleteById(TEST_ID_2);
    }

    @Test
    void updateCertificateSuccess() {
        when(certificateRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_CERTIFICATE_1));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(TEST_CERTIFICATE_1);
        when(certificateMapper.toDto(TEST_CERTIFICATE_1)).thenReturn(TEST_CERTIFICATE_DTO);
        when(bankDetailsRepository.findById(TEST_CERTIFICATE_DTO.getBankDetailsId()))
                .thenReturn(Optional.of(TEST_CERTIFICATE_1.getBankDetails()));
        CertificateDto result = certificateService.updateCertificate(TEST_ID_1, TEST_CERTIFICATE_DTO);
        assertNotNull(result);
        verify(certificateRepository).findById(TEST_ID_1);
        verify(certificateRepository).save(any(Certificate.class));
        verify(certificateMapper).toDto(TEST_CERTIFICATE_1);
        verify(bankDetailsRepository).findById(TEST_CERTIFICATE_DTO.getBankDetailsId());
    }

    @Test
    void updateCertificateFailure() {
        when(certificateRepository.findById(TEST_ID_2)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> certificateService.updateCertificate(TEST_ID_2, TEST_CERTIFICATE_DTO));
        assertTrue(exception.getMessage().contains("Certificate not found with id " + TEST_ID_2));
        verify(certificateRepository).findById(TEST_ID_2);
    }
}
