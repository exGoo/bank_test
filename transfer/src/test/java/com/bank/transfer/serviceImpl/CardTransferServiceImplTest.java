package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.repository.CardTransferRepository;
import com.bank.transfer.service.impl.CardTransferServiceImpl;
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
class CardTransferServiceImplTest {
    public static final Long ACCOUNT_TRANSFER_ID = 1L;

    @Mock
    private CardTransferRepository cardTransferRepository;
    @Mock
    private CardTransferMapper mapper;
    @InjectMocks
    private CardTransferServiceImpl cardTransferService;

    CardTransferDTO cardTransferDTO1;
    CardTransferDTO cardTransferDTO2;
    CardTransfer cardTransfer1;
    CardTransfer cardTransfer2;
    List<CardTransfer> cardTransferList = new ArrayList<>();
    List<CardTransferDTO> cardTransferListDTO = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        cardTransfer1 = CardTransfer.builder()
                .id(1L)
                .cardNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        cardTransfer2 = CardTransfer.builder()
                .id(2L)
                .cardNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();
        cardTransferDTO1 = CardTransferDTO.builder()
                .id(1L)
                .cardNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        cardTransferDTO2 = CardTransferDTO.builder()
                .id(2L)
                .cardNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();

        cardTransferList.add(cardTransfer1);
        cardTransferList.add(cardTransfer2);

        cardTransferListDTO.add(cardTransferDTO1);
        cardTransferListDTO.add(cardTransferDTO2);
    }

    @Test
    void getCardTransferById() {
        when(cardTransferRepository.findById(1L)).thenReturn(Optional.of(cardTransfer1));
        when(mapper.cardTransferToCardTransferDTO(cardTransfer1)).thenReturn(cardTransferDTO1);
        Optional<CardTransferDTO> optionalCardTransferDTO = cardTransferService.getCardTransferById(1L);
        CardTransferDTO cardTransferDTO = optionalCardTransferDTO.get();
        assertNotNull(cardTransferDTO);
        assertEquals(cardTransferDTO1, cardTransferDTO);
    }

    @Test
    void allCardTransfer() {
        when(cardTransferRepository.findAll())
                .thenReturn(List.of(cardTransfer1, cardTransfer2));
        when(mapper.cardTransferListToDTOList(List.of(cardTransfer1, cardTransfer2)))
                .thenReturn(List.of(cardTransferDTO1, cardTransferDTO2));
        List<CardTransferDTO> allCardTransfersDTO = cardTransferService.allCardTransfer();
        assertEquals(allCardTransfersDTO, cardTransferListDTO);
    }

    @Test
    void saveCardTransfer() {

        when(mapper.cardTransferDTOToCardTransfer(cardTransferDTO1)).thenReturn(cardTransfer1);
        when(cardTransferRepository.save(cardTransfer1)).thenReturn(cardTransfer1);
        CardTransfer savedCardTransfer = cardTransferService.saveCardTransfer(cardTransferDTO1);

        assertNotNull(savedCardTransfer);
        assertEquals(cardTransfer1, savedCardTransfer);
    }

//    @Test
//    void testUpdateCardTransferById() {
//
//        when(cardTransferRepository.findById(2L)).thenReturn(Optional.of(cardTransfer2));
//        when(cardTransferRepository.save(any(CardTransfer.class))).thenReturn(cardTransfer2);
//
//        CardTransfer updatedCardTransfer = cardTransferService.updateCardTransferById(cardTransferDTO2, 2L);
//
//        assertNotNull(updatedCardTransfer);
//        assertEquals(2L, updatedCardTransfer.getCardNumber());
//        assertEquals(new BigDecimal("34.34"), updatedCardTransfer.getAmount());
//        assertEquals("purpose2", updatedCardTransfer.getPurpose());
//        assertEquals(6L, updatedCardTransfer.getCardDetailsId());
//
//
//        verify(cardTransferRepository).findById(1L);
//        verify(mapper).cardTransferDTOToCardTransfer(cardTransferDTO2);
//        verify(cardTransferRepository).save(cardTransfer1);
//    }

    @Test
    void updateCardTransferById_ShouldThrowException_WhenCardTransferDTOIsNull() {
        // Проверка на null
        assertThrows(IllegalArgumentException.class, () -> {
            cardTransferService.updateCardTransferById(null, 1L);
        });
    }

    @Test
    void updateCardTransferById_ShouldThrowException_WhenCardTransferNotFound() {
        // Настройка поведения мока
        when(cardTransferRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка на отсутствие сущности
        assertThrows(EntityNotFoundException.class, () -> {
            cardTransferService.updateCardTransferById(cardTransferDTO1, 1L);
        });
    }

    @Test
    void deleteCardTransfer() {
        cardTransferService.deleteCardTransfer(ACCOUNT_TRANSFER_ID);
        verify(cardTransferRepository).deleteById(ACCOUNT_TRANSFER_ID);
    }
}
