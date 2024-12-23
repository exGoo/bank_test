package com.bank.transfer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardTransferTest {
    private CardTransfer cardTransfer;

    @BeforeEach
    public void creatNewCardTransfer() {
        cardTransfer = new CardTransfer();
    }

    @Test
    void testSettersAndGetters() {
        cardTransfer.setId(100L);
        assertEquals(100L, cardTransfer.getId());
        cardTransfer.setCardNumber(123456789L);
        assertEquals(123456789L, cardTransfer.getCardNumber());
        cardTransfer.setAmount(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1000.00"), cardTransfer.getAmount());
        cardTransfer.setPurpose("Test transfer");
        assertEquals("Test transfer", cardTransfer.getPurpose());
        cardTransfer.setAccountDetailsId(200L);
        assertEquals(200L, cardTransfer.getAccountDetailsId());
    }

    @Test
    void testBuilder() {
        CardTransfer transfer = CardTransfer.builder()
                .cardNumber(123456789L)
                .amount(new BigDecimal("500.00"))
                .purpose("Payment")
                .accountDetailsId(300L)
                .build();
        assertNotNull(transfer);
        assertEquals(123456789L, transfer.getCardNumber());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals("Payment", transfer.getPurpose());
        assertEquals(300L, transfer.getAccountDetailsId());
    }

    @Test
    void testConstructor() {
        CardTransfer transfer = new CardTransfer(123456789L, new BigDecimal("500.00"), "Payment", 300L);
        assertNotNull(transfer);
        assertEquals(123456789L, transfer.getCardNumber());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals("Payment", transfer.getPurpose());
        assertEquals(300L, transfer.getAccountDetailsId());
    }

    @Test
    void testToString() {
        cardTransfer.setId(100L);
        cardTransfer.setCardNumber(123456789L);
        cardTransfer.setAmount(new BigDecimal("1000.00"));
        cardTransfer.setPurpose("Test transfer");
        cardTransfer.setAccountDetailsId(200L);
        String expectedString = "CardTransfer(id=100, cardNumber=123456789, amount=1000.00, purpose=Test transfer, accountDetailsId=200)";
        assertEquals(expectedString, cardTransfer.toString());
    }
}
