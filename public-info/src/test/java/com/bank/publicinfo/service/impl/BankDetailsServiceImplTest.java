package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
class BankDetailsServiceImplTest {

    @InjectMocks
    private BankDetailsServiceImpl bankDetailsService;

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @Mock
    private BankDetailsMapper bankDetailsMapper;

    private BankDetails bankDetails;

    private BankDetailsDto detailsDto;

    @BeforeEach
    public void setUp() {
        bankDetails = new BankDetails();
        bankDetails.setId(1L);
        bankDetails.setCity("Moscow");
        detailsDto = new BankDetailsDto();
        detailsDto.setId(1L);
        detailsDto.setCity("Moscow");
    }

    @Test
    void findByIdSucces() {
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.of(bankDetails));
        when(bankDetailsMapper.toDto(bankDetails)).thenReturn(detailsDto);
        BankDetailsDto result = bankDetailsService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bankDetailsRepository, times(1)).findById(1L);
        verify(bankDetailsMapper, times(1)).toDto(bankDetails);
    }

    @Test
    void findByIdFailure() {
        Long id = 1L;
        when(bankDetailsRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bankDetailsService.findById(id);
        });
        assertEquals("Bank Details not found with id " + id, exception.getMessage());
        verify(bankDetailsRepository).findById(id);
    }

    @Test
    void findAllWithRelations() {
        List<BankDetails> detailsList = List.of(bankDetails);
        when(bankDetailsRepository.findAll()).thenReturn(detailsList);
        when(bankDetailsMapper.toDto(bankDetails)).thenReturn(detailsDto);
        List<BankDetailsDto> result = bankDetailsService.findAllWithRelations();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bankDetailsRepository, times(1)).findAll();
        verify(bankDetailsMapper, times(1)).toDto(bankDetails);
    }

    @Test
    void findByCitySucces() {
        String city = "Moscow";
        List<BankDetails> bankDetailsList = Arrays.asList(bankDetails);
        BankDetailsDto bankDetailsDto = new BankDetailsDto(1L, city);
        when(bankDetailsRepository.findByCity(city)).thenReturn(bankDetailsList);
        when(bankDetailsMapper.toDto(bankDetails)).thenReturn(bankDetailsDto);
        List<BankDetailsDto> result = bankDetailsService.findByCity(city);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bankDetailsDto, result.get(0));
    }

    @Test
    void findByCityEmptyListTest() {
        String city = "UnknownCity";
        List<BankDetails> emptyList = new ArrayList<>();
        when(bankDetailsRepository.findByCity(city)).thenReturn(emptyList);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bankDetailsService.findByCity(city);
        });
        assertEquals("City not found " + city, exception.getMessage());
    }

    @Test
    void findByCityFailure() {
        String city = "New York";
        when(bankDetailsRepository.findByCity(city)).thenThrow(new RuntimeException("Database error"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bankDetailsService.findByCity(city);
        });
        assertEquals("City not found New York", exception.getMessage());
    }

    @Test
    void addBankDetails() {
        when(bankDetailsMapper.toModel(detailsDto)).thenReturn(bankDetails);
        when(bankDetailsRepository.save(bankDetails)).thenReturn(bankDetails);
        when(bankDetailsMapper.toDto(bankDetails)).thenReturn(detailsDto);
        BankDetailsDto result = bankDetailsService.addBankDetails(detailsDto);
        assertNotNull(result);
        verify(bankDetailsRepository, times(1)).save(bankDetails);
        verify(bankDetailsMapper, times(1)).toModel(detailsDto);
        verify(bankDetailsMapper, times(1)).toDto(bankDetails);
    }

    @Test
    void deleteBankDetailsByIdSucces() {
        doNothing().when(bankDetailsRepository).deleteById(1L);
        bankDetailsService.deleteBankDetailsById(1L);
        verify(bankDetailsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBankDetailsByIdFailure() {
        doThrow(new EntityNotFoundException("BankDetails not found with id 1")).when(bankDetailsRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> bankDetailsService.deleteBankDetailsById(1L));
        verify(bankDetailsRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateBankDetailsSucces() {
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.of(bankDetails));
        when(bankDetailsRepository.save(any(BankDetails.class))).thenReturn(bankDetails);
        when(bankDetailsMapper.toDto(bankDetails)).thenReturn(detailsDto);
        BankDetailsDto result = bankDetailsService.updateBankDetails(1L, detailsDto);
        assertNotNull(result);
        verify(bankDetailsRepository, times(1)).findById(1L);
        verify(bankDetailsRepository, times(1)).save(any(BankDetails.class));
        verify(bankDetailsMapper, times(1)).toDto(bankDetails);
    }

    @Test
    void updateBankDetailsFailure() {
        when(bankDetailsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bankDetailsService.updateBankDetails(1L, detailsDto));
        verify(bankDetailsRepository, times(1)).findById(1L);
    }
}
