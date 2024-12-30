package com.bank.transfer.mapper;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.bank.transfer.ResourcesForTests.cardTransfer1;
import static com.bank.transfer.ResourcesForTests.cardTransfer2;
import static com.bank.transfer.ResourcesForTests.cardTransferDTO1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardTransferMapperTest {
    private final CardTransferMapper mapper = CardTransferMapper.INSTANCE;

    @Test
    void testCardTransferDTOToCardTransfer() {
        CardTransfer cardTransfer =
                mapper.cardTransferDTOToCardTransfer(cardTransferDTO1);
        assertNotNull(cardTransfer);
        assertEquals(cardTransferDTO1.getCardNumber(), cardTransfer.getCardNumber());
        assertEquals(cardTransferDTO1.getAmount(), cardTransfer.getAmount());
        assertEquals(cardTransferDTO1.getPurpose(), cardTransfer.getPurpose());
        assertEquals(cardTransferDTO1.getAccountDetailsId(), cardTransfer.getAccountDetailsId());
    }

    @Test
    void testCardTransferToCardTransferDTO() {
        CardTransferDTO cardTransferDTO1 =
                mapper.cardTransferToCardTransferDTO(cardTransfer1);
        assertNotNull(cardTransferDTO1);
        assertEquals(cardTransfer1.getCardNumber(), cardTransferDTO1.getCardNumber());
        assertEquals(cardTransfer1.getAmount(), cardTransferDTO1.getAmount());
        assertEquals(cardTransfer1.getPurpose(), cardTransferDTO1.getPurpose());
        assertEquals(cardTransfer1.getAccountDetailsId(), cardTransferDTO1.getAccountDetailsId());
    }

    @Test
    void testCardTransferListToDTOList() {
        List<CardTransfer> cardTransferList =
                Arrays.asList(cardTransfer1, cardTransfer2);
        List<CardTransferDTO> cardTransferDTOList =
                mapper.cardTransferListToDTOList(cardTransferList);
        assertNotNull(cardTransferDTOList);
        assertEquals(2, cardTransferDTOList.size());

        CardTransferDTO dto1 = cardTransferDTOList.get(0);
        assertEquals(cardTransfer1.getId(), dto1.getId());
        assertEquals(cardTransfer1.getCardNumber(), dto1.getCardNumber());
        assertEquals(cardTransfer1.getAmount(), dto1.getAmount());
        assertEquals(cardTransfer1.getPurpose(), dto1.getPurpose());
        assertEquals(cardTransfer1.getAccountDetailsId(), dto1.getAccountDetailsId());

        CardTransferDTO dto2 = cardTransferDTOList.get(1);
        assertEquals(cardTransfer2.getId(), dto2.getId());
        assertEquals(cardTransfer2.getCardNumber(), dto2.getCardNumber());
        assertEquals(cardTransfer2.getAmount(), dto2.getAmount());
        assertEquals(cardTransfer2.getPurpose(), dto2.getPurpose());
        assertEquals(cardTransfer2.getAccountDetailsId(), dto2.getAccountDetailsId());
    }
}
