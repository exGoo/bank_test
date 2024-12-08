package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.mapper.ATMMapper;
import com.bank.publicinfo.repository.ATMRepository;
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
class ATMServiceImplTest {

    @InjectMocks
    private ATMServiceImpl atmService;

    @Mock
    private ATMRepository atmRepository;

    @Mock
    private ATMMapper atmMapper;

    private ATM atm;

    private ATMDto atmDto;

    @BeforeEach
    public void setUp() {
        atm = new ATM();
        atm.setId(1L);
        atmDto = new ATMDto();
        atmDto.setId(1L);
    }

    @Test
    void findByIdSucces() {
        when(atmRepository.findById(1L)).thenReturn(Optional.of(atm));
        when(atmMapper.toDto(atm)).thenReturn(atmDto);
        ATMDto result = atmService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(atmRepository, times(1)).findById(1L);
        verify(atmMapper, times(1)).toDto(atm);
    }

    @Test
    void findByIdFailure() {
        Long id = 1L;
        when(atmRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            atmService.findById(id);
        });
        assertEquals("ATM not found with id " + id, exception.getMessage());
        verify(atmRepository).findById(id);
    }

    @Test
    void findAll() {
        List<ATM> atmList = List.of(atm);
        when(atmRepository.findAll()).thenReturn(atmList);
        when(atmMapper.toDto(atm)).thenReturn(atmDto);
        List<ATMDto> result = atmService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(atmRepository, times(1)).findAll();
        verify(atmMapper, times(1)).toDto(atm);
    }

    @Test
    void addATM() {
        when(atmMapper.toModel(atmDto)).thenReturn(atm);
        when(atmRepository.save(atm)).thenReturn(atm);
        when(atmMapper.toDto(atm)).thenReturn(atmDto);
        ATMDto result = atmService.addATM(atmDto);
        assertNotNull(result);
        verify(atmRepository, times(1)).save(atm);
        verify(atmMapper, times(1)).toModel(atmDto);
        verify(atmMapper, times(1)).toDto(atm);
    }

    @Test
    void deleteATMByIdSucces() {
        doNothing().when(atmRepository).deleteById(1L);
        atmService.deleteATMById(1L);
        verify(atmRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteATMByIdFailure() {
        doThrow(new EntityNotFoundException("ATM not found with id 1")).when(atmRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> atmService.deleteATMById(1L));
        verify(atmRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateATMSucces() {
        when(atmRepository.findById(1L)).thenReturn(Optional.of(atm));
        when(atmRepository.save(any(ATM.class))).thenReturn(atm);
        when(atmMapper.toDto(atm)).thenReturn(atmDto);
        ATMDto result = atmService.updateATM(1L, atmDto);
        assertNotNull(result);
        verify(atmRepository, times(1)).findById(1L);
        verify(atmRepository, times(1)).save(any(ATM.class));
        verify(atmMapper, times(1)).toDto(atm);
    }

    @Test
    void updateATMFailure() {
        when(atmRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> atmService.updateATM(1L, atmDto));
        verify(atmRepository, times(1)).findById(1L);
    }
}
