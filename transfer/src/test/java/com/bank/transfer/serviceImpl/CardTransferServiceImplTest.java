package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.repository.CardTransferRepository;
import com.bank.transfer.service.impl.CardTransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_2;
import static com.bank.transfer.ResourcesForTests.AMOUNT_2;
import static com.bank.transfer.ResourcesForTests.CARD_NUMBER_2;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.PURPOSE_2;
import static com.bank.transfer.ResourcesForTests.cardTransfer1;
import static com.bank.transfer.ResourcesForTests.cardTransfer2;
import static com.bank.transfer.ResourcesForTests.cardTransferDTO1;
import static com.bank.transfer.ResourcesForTests.cardTransferDTO2;
import static com.bank.transfer.ResourcesForTests.cardTransferListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardTransferServiceImplTest {

    @Mock
    private CardTransferRepository cardTransferRepository;
    @Mock
    private CardTransferMapper mapper;
    @InjectMocks
    private CardTransferServiceImpl cardTransferService;

    @Test
    void getCardTransferById() {
        when(cardTransferRepository.findById(ID_1)).thenReturn(Optional.of(cardTransfer1));
        when(mapper.cardTransferToCardTransferDTO(cardTransfer1)).thenReturn(cardTransferDTO1);
        Optional<CardTransferDTO> optionalCardTransferDTO = cardTransferService.getCardTransferById(ID_1);
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

    @Test
    void testUpdateCardTransferById() {

        // Настройка моков
        when(cardTransferRepository.findById(ID_1)).thenReturn(Optional.of(cardTransfer1));
        when(cardTransferRepository.save(any(CardTransfer.class))).thenReturn(cardTransfer1);

        // Вызов метода
        CardTransfer updatedCardTransfer = cardTransferService.updateCardTransferById(cardTransferDTO2, ID_1);

        // Проверки
        assertNotNull(updatedCardTransfer);
        assertEquals(CARD_NUMBER_2, updatedCardTransfer.getCardNumber());
        assertEquals(AMOUNT_2, updatedCardTransfer.getAmount());
        assertEquals(PURPOSE_2, updatedCardTransfer.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_2, updatedCardTransfer.getAccountDetailsId());
        // Проверка вызовов методов
        verify(cardTransferRepository).findById(ID_1);
        verify(cardTransferRepository).save(cardTransfer1);
    }

    @Test
    void updateCardTransferById_ShouldThrowException_WhenCardTransferNotFound() {
        // Настройка поведения мока
        when(cardTransferRepository.findById(ID_1)).thenReturn(Optional.empty());

        // Проверка на отсутствие сущности
        assertThrows(EntityNotFoundException.class, () -> {
            cardTransferService.updateCardTransferById(cardTransferDTO1, ID_1);
        });
    }

    @Test
    void deleteCardTransfer() {
        cardTransferService.deleteCardTransfer(ID_1);
        verify(cardTransferRepository).deleteById(ID_1);
    }
}
