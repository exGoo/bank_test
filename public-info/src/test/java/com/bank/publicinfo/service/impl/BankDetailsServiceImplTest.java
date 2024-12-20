package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BANK_DETAILS;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BANK_DETAILS_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_DETAILS_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_DETAILS_DTO_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_DETAILS_LIST_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_DETAILS_LIST_BY_CITY;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_DETAILS;
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
class BankDetailsServiceImplTest {

    @InjectMocks
    private BankDetailsServiceImpl bankDetailsService;

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private BankDetailsMapper bankDetailsMapper;

    private static final ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

    private static final ArgumentCaptor<BankDetails> detailsArgumentCaptor = ArgumentCaptor.forClass(BankDetails.class);

    private static final ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    void findByIdSuccess() {
        when(bankDetailsRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_BANK_DETAILS));
        when(bankDetailsMapper.toDto(TEST_BANK_DETAILS)).thenReturn(TEST_DETAILS_DTO);
        BankDetailsDto result = bankDetailsService.findById(TEST_ID_1);
        assertNotNull(result);
        assertEquals(TEST_DETAILS_DTO,result);
        verify(bankDetailsRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
        verify(bankDetailsMapper).toDto(TEST_BANK_DETAILS);
    }

    @Test
    void findByIdFailure() {
        when(bankDetailsRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bankDetailsService.findById(TEST_ID_1);
        });
        assertTrue(exception.getMessage().contains("Bank Details not found with id " + TEST_ID_1));
        verify(bankDetailsRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }

    @Test
    void findAllWithRelations() {
        List<BankDetailsDto> detailsList = List.of(TEST_DETAILS_DTO);
        when(bankDetailsRepository.findAll()).thenReturn(TEST_DETAILS_LIST_2);
        when(bankDetailsMapper.toDto(TEST_BANK_DETAILS)).thenReturn(TEST_DETAILS_DTO);
        List<BankDetailsDto> result = bankDetailsService.findAllWithRelations();
        assertNotNull(result);
        assertEquals(detailsList, result);
        verify(bankDetailsRepository).findAll();
        verify(bankDetailsMapper).toDto(TEST_BANK_DETAILS);
    }

    @Test
    void findAllElseEmptyList() {
        when(bankDetailsRepository.findAll()).thenReturn(Collections.emptyList());
        List<BankDetailsDto> result = bankDetailsService.findAllWithRelations();
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCitySuccess() {
        when(bankDetailsRepository.findByCity(TEST_CITY_1)).thenReturn(TEST_DETAILS_LIST_BY_CITY);
        when(bankDetailsMapper.toDto(TEST_BANK_DETAILS)).thenReturn(TEST_DETAILS_DTO);
        when(bankDetailsMapper.toDto(TEST_BANK_DETAILS_2)).thenReturn(TEST_DETAILS_DTO_2);
        List<BankDetailsDto> result = bankDetailsService.findByCity(TEST_CITY_1);
        assertNotNull(result);
        assertEquals(TEST_LIST_DETAILS,result);
        verify(bankDetailsRepository).findByCity(stringArgumentCaptor.capture());
        assertEquals(TEST_CITY_1, stringArgumentCaptor.getValue());
        verify(bankDetailsMapper).toDto(TEST_BANK_DETAILS);
        verify(bankDetailsMapper).toDto(TEST_BANK_DETAILS_2);
    }

    @Test
    void findByCityEmptyListTest() {
        when(bankDetailsRepository.findByCity(TEST_CITY_2)).thenReturn(Collections.emptyList());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bankDetailsService.findByCity(TEST_CITY_2);
        });
        assertTrue(exception.getMessage().contains("City not found " + TEST_CITY_2));
    }

    @Test
    void findByCityFailure() {
        when(bankDetailsRepository.findByCity(TEST_CITY_2)).thenThrow(new RuntimeException("Database error"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bankDetailsService.findByCity(TEST_CITY_2);
        });
        assertTrue(exception.getMessage().contains("City not found " + TEST_CITY_2));
    }

    @Test
    void addBankDetails() {
        when(bankDetailsMapper.toModel(TEST_DETAILS_DTO)).thenReturn(TEST_BANK_DETAILS);
        when(bankDetailsRepository.save(TEST_BANK_DETAILS)).thenReturn(TEST_BANK_DETAILS);
        when(bankDetailsMapper.toDto(TEST_BANK_DETAILS)).thenReturn(TEST_DETAILS_DTO);
        BankDetailsDto result = bankDetailsService.addBankDetails(TEST_DETAILS_DTO);
        assertNotNull(result);
        verify(bankDetailsRepository).save(detailsArgumentCaptor.capture());
        assertEquals(TEST_BANK_DETAILS, detailsArgumentCaptor.getValue());
        verify(bankDetailsMapper).toModel(TEST_DETAILS_DTO);
        verify(bankDetailsMapper).toDto(TEST_BANK_DETAILS);
    }

    @Test
    void deleteBankDetailsByIdSuccess() {
        doNothing().when(bankDetailsRepository).deleteById(TEST_ID_1);
        bankDetailsService.deleteBankDetailsById(TEST_ID_1);
        verify(bankDetailsRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }

    @Test
    void deleteBankDetailsByIdFailure() {
        doThrow(new EntityNotFoundException("BankDetails not found with id " + TEST_ID_1))
                .when(bankDetailsRepository).deleteById(TEST_ID_1);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bankDetailsService.deleteBankDetailsById(TEST_ID_1));
        assertTrue(exception.getMessage().contains("BankDetails not found with id " + TEST_ID_1));
        verify(bankDetailsRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }

    @Test
    void updateBankDetailsSuccess() {
        when(bankDetailsRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_BANK_DETAILS));
        when(bankDetailsRepository.save(any(BankDetails.class))).thenReturn(TEST_BANK_DETAILS);
        when(bankDetailsMapper.toDto(TEST_BANK_DETAILS)).thenReturn(TEST_DETAILS_DTO);
        BankDetailsDto result = bankDetailsService.updateBankDetails(TEST_ID_1, TEST_DETAILS_DTO);
        assertNotNull(result);
        verify(bankDetailsRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
        verify(bankDetailsRepository).save(detailsArgumentCaptor.capture());
        assertEquals(TEST_BANK_DETAILS, detailsArgumentCaptor.getValue());
        verify(bankDetailsMapper).toDto(TEST_BANK_DETAILS);
    }

    @Test
    void updateBankDetailsFailure() {
        when(bankDetailsRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bankDetailsService.updateBankDetails(TEST_ID_1, TEST_DETAILS_DTO));
        assertTrue(exception.getMessage().contains("Bank Details not found with id " + TEST_ID_1));
        verify(bankDetailsRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }
}
