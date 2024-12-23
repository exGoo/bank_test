package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.impl.PhoneTransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneTransferServiceImplTest {
    public static final Long PHONE_TRANSFER_ID = 1L;

    @Mock
    private PhoneTransferRepository phoneTransferRepository;
    @Mock
    private PhoneTransferMapper mapper;
    @InjectMocks
    private PhoneTransferServiceImpl phoneTransferService;

    PhoneTransferDTO phoneTransferDTO1;
    PhoneTransferDTO phoneTransferDTO2;
    PhoneTransfer phoneTransfer1;
    PhoneTransfer phoneTransfer2;
    List<PhoneTransfer> phoneTransferList = new ArrayList<>();
    List<PhoneTransferDTO> phoneTransferListDTO = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        phoneTransfer1 = PhoneTransfer.builder()
                .id(1L)
                .phoneNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        phoneTransfer2 = PhoneTransfer.builder()
                .id(2L)
                .phoneNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();
        phoneTransferDTO1 = PhoneTransferDTO.builder()
                .id(1L)
                .phoneNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        phoneTransferDTO2 = PhoneTransferDTO.builder()
                .id(2L)
                .phoneNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();

        phoneTransferList.add(phoneTransfer1);
        phoneTransferList.add(phoneTransfer2);

        phoneTransferListDTO.add(phoneTransferDTO1);
        phoneTransferListDTO.add(phoneTransferDTO2);
    }

    @Test
    void getPhoneTransferById() {
        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.of(phoneTransfer1));
        when(mapper.phoneTransferToPhoneTransferDTO(phoneTransfer1)).thenReturn(phoneTransferDTO1);
        Optional<PhoneTransferDTO> optionalPhoneTransferDTO = phoneTransferService.getPhoneTransferById(1L);
        PhoneTransferDTO phoneTransferDTO = optionalPhoneTransferDTO.get();
        assertNotNull(phoneTransferDTO);
        assertEquals(phoneTransferDTO1, phoneTransferDTO);
    }

    @Test
    void allPhoneTransfer() {
        when(phoneTransferRepository.findAll())
                .thenReturn(List.of(phoneTransfer1, phoneTransfer2));
        when(mapper.phoneTransferListToDTOList(List.of(phoneTransfer1, phoneTransfer2)))
                .thenReturn(List.of(phoneTransferDTO1, phoneTransferDTO2));
        List<PhoneTransferDTO> allPhoneTransfersDTO = phoneTransferService.allPhoneTransfer();
        assertEquals(allPhoneTransfersDTO, phoneTransferListDTO);
    }

    @Test
    void savePhoneTransfer() {

        when(mapper.phoneTransferDTOToPhoneTransfer(phoneTransferDTO1)).thenReturn(phoneTransfer1);
        when(phoneTransferRepository.save(phoneTransfer1)).thenReturn(phoneTransfer1);
        PhoneTransfer savedPhoneTransfer = phoneTransferService.savePhoneTransfer(phoneTransferDTO1);

        assertNotNull(savedPhoneTransfer);
        assertEquals(phoneTransfer1, savedPhoneTransfer);
    }

//    @Test
//    void testUpdatePhoneTransferById() {
//
//        when(phoneTransferRepository.findById(2L)).thenReturn(Optional.of(phoneTransfer2));
//        when(phoneTransferRepository.save(any(PhoneTransfer.class))).thenReturn(phoneTransfer2);
//
//        PhoneTransfer updatedPhoneTransfer = phoneTransferService.updatePhoneTransferById(phoneTransferDTO2, 2L);
//
//        assertNotNull(updatedPhoneTransfer);
//        assertEquals(2L, updatedPhoneTransfer.getPhoneNumber());
//        assertEquals(new BigDecimal("34.34"), updatedPhoneTransfer.getAmount());
//        assertEquals("purpose2", updatedPhoneTransfer.getPurpose());
//        assertEquals(6L, updatedPhoneTransfer.getPhoneDetailsId());
//
//
//        verify(phoneTransferRepository).findById(1L);
//        verify(mapper).phoneTransferDTOToPhoneTransfer(phoneTransferDTO2);
//        verify(phoneTransferRepository).save(phoneTransfer1);
//    }

    @Test
    void updatePhoneTransferById_ShouldThrowException_WhenPhoneTransferDTOIsNull() {
        // Проверка на null
        assertThrows(IllegalArgumentException.class, () -> {
            phoneTransferService.updatePhoneTransferById(null, 1L);
        });
    }

    @Test
    void updatePhoneTransferById_ShouldThrowException_WhenPhoneTransferNotFound() {
        // Настройка поведения мока
        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка на отсутствие сущности
        assertThrows(EntityNotFoundException.class, () -> {
            phoneTransferService.updatePhoneTransferById(phoneTransferDTO1, 1L);
        });
    }

    @Test
    void deletePhoneTransfer() {
        phoneTransferService.deletePhoneTransfer(PHONE_TRANSFER_ID);
        verify(phoneTransferRepository).deleteById(PHONE_TRANSFER_ID);
    }
}
