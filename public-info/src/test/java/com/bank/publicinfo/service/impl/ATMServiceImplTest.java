package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.mapper.ATMMapper;
import com.bank.publicinfo.repository.ATMRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_LIST;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
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
class ATMServiceImplTest {

    @InjectMocks
    private ATMServiceImpl atmService;

    @Mock
    private ATMRepository atmRepository;

    @Mock
    private ATMMapper atmMapper;

    @Test
    void findByIdSuccess() {
        when(atmRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_ATM_1));
        when(atmMapper.toDto(TEST_ATM_1)).thenReturn(TEST_ATM_DTO);
        ATMDto result = atmService.findById(TEST_ID_1);
        assertNotNull(result);
        assertEquals(TEST_ATM_DTO,result);
        verify(atmRepository).findById(TEST_ID_1);
        verify(atmMapper).toDto(TEST_ATM_1);
    }

    @Test
    void findByIdFailure() {
        when(atmRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            atmService.findById(TEST_ID_1);
        });
        assertTrue(exception.getMessage().contains("ATM not found with id" + TEST_ID_1));
        verify(atmRepository).findById(TEST_ID_1);
    }

    @Test
    void findAll() {
        List<ATMDto> expectedDtos = List.of(TEST_ATM_DTO);
        when(atmRepository.findAll()).thenReturn(TEST_ATM_LIST);
        when(atmMapper.toDto(TEST_ATM_1)).thenReturn(TEST_ATM_DTO);
        List<ATMDto> result = atmService.findAll();
        assertNotNull(result);
        assertEquals(expectedDtos, result);
        verify(atmRepository).findAll();
        verify(atmMapper).toDto(TEST_ATM_1);
    }

    @Test
    void addATM() {
        when(atmMapper.toModel(TEST_ATM_DTO)).thenReturn(TEST_ATM_1);
        when(atmRepository.save(TEST_ATM_1)).thenReturn(TEST_ATM_1);
        when(atmMapper.toDto(TEST_ATM_1)).thenReturn(TEST_ATM_DTO);
        ATMDto result = atmService.addATM(TEST_ATM_DTO);
        assertNotNull(result);
        verify(atmRepository).save(TEST_ATM_1);
        verify(atmMapper).toModel(TEST_ATM_DTO);
        verify(atmMapper).toDto(TEST_ATM_1);
    }

    @Test
    void deleteATMByIdSuccess() {
        doNothing().when(atmRepository).deleteById(TEST_ID_1);
        atmService.deleteATMById(TEST_ID_1);
        verify(atmRepository).deleteById(TEST_ID_1);
    }

    @Test
    void deleteATMByIdFailure() {
        doThrow(new EntityNotFoundException("ATM not found with id 1")).when(atmRepository).deleteById(TEST_ID_1);
        assertThrows(EntityNotFoundException.class, () -> atmService.deleteATMById(TEST_ID_1));
        verify(atmRepository).deleteById(TEST_ID_1);
    }

    @Test
    void updateATMSuccess() {
        when(atmRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_ATM_1));
        when(atmRepository.save(any(ATM.class))).thenReturn(TEST_ATM_1);
        when(atmMapper.toDto(TEST_ATM_1)).thenReturn(TEST_ATM_DTO);
        ATMDto result = atmService.updateATM(TEST_ID_1, TEST_ATM_DTO);
        assertNotNull(result);
        verify(atmRepository).findById(TEST_ID_1);
        verify(atmRepository).save(any(ATM.class));
        verify(atmMapper).toDto(TEST_ATM_1);
    }

    @Test
    void updateATMFailure() {
        when(atmRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> atmService.updateATM(TEST_ID_1, TEST_ATM_DTO));
        verify(atmRepository).findById(TEST_ID_1);
    }
}
