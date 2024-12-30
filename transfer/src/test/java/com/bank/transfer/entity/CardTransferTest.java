package com.bank.transfer.entity;

import org.junit.jupiter.api.Test;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_1;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_2;
import static com.bank.transfer.ResourcesForTests.AMOUNT_1;
import static com.bank.transfer.ResourcesForTests.AMOUNT_2;
import static com.bank.transfer.ResourcesForTests.CARD_NUMBER_1;
import static com.bank.transfer.ResourcesForTests.CARD_NUMBER_2;
import static com.bank.transfer.ResourcesForTests.CARD_TO_STRING;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.ID_2;
import static com.bank.transfer.ResourcesForTests.PURPOSE_1;
import static com.bank.transfer.ResourcesForTests.PURPOSE_2;
import static com.bank.transfer.ResourcesForTests.accountTransfer1;
import static com.bank.transfer.ResourcesForTests.cardTransfer;
import static com.bank.transfer.ResourcesForTests.cardTransfer1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardTransferTest {
    @Test
    void testSettersAndGetters() {
        cardTransfer1.setId(ID_2);
        assertEquals(ID_2, cardTransfer1.getId());
        cardTransfer1.setCardNumber(CARD_NUMBER_2);
        assertEquals(CARD_NUMBER_2, cardTransfer1.getCardNumber());
        cardTransfer1.setAmount(AMOUNT_2);
        assertEquals(AMOUNT_2, cardTransfer1.getAmount());
        cardTransfer1.setPurpose(PURPOSE_2);
        assertEquals(PURPOSE_2, cardTransfer1.getPurpose());
        accountTransfer1.setAccountDetailsId(ACCOUNT_DETAILS_ID_2);
        assertEquals(ACCOUNT_DETAILS_ID_2, accountTransfer1.getAccountDetailsId());
    }

    @Test
    void testBuilder() {
        assertNotNull(cardTransfer1);
        assertEquals(CARD_NUMBER_2, cardTransfer1.getCardNumber());
        assertEquals(AMOUNT_2, cardTransfer1.getAmount());
        assertEquals(PURPOSE_2, cardTransfer1.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_1, cardTransfer1.getAccountDetailsId());
    }

    @Test
    void testConstructor() {
        assertNotNull(cardTransfer);
        assertEquals(CARD_NUMBER_1, cardTransfer.getCardNumber());
        assertEquals(AMOUNT_1, cardTransfer.getAmount());
        assertEquals(PURPOSE_1, cardTransfer.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_1, cardTransfer.getAccountDetailsId());
    }

    @Test
    void testToString() {
        cardTransfer1.setId(ID_1);
        cardTransfer1.setCardNumber(CARD_NUMBER_1);
        cardTransfer1.setAmount(AMOUNT_1);
        cardTransfer1.setPurpose(PURPOSE_1);
        cardTransfer1.setAccountDetailsId(ACCOUNT_DETAILS_ID_1);
        assertEquals(CARD_TO_STRING, cardTransfer1.toString());
    }
}
