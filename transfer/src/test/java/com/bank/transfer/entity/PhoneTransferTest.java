package com.bank.transfer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PhoneTransferTest {
    private PhoneTransfer phoneTransfer;

    @BeforeEach
    public void creatNewPhoneTransfer() {
        phoneTransfer = new PhoneTransfer();
    }

    @Test
    void testSettersAndGetters() {
        phoneTransfer.setId(100L);
        assertEquals(100L, phoneTransfer.getId());
        phoneTransfer.setPhoneNumber(123456789L);
        assertEquals(123456789L, phoneTransfer.getPhoneNumber());
        phoneTransfer.setAmount(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1000.00"), phoneTransfer.getAmount());
        phoneTransfer.setPurpose("Test transfer");
        assertEquals("Test transfer", phoneTransfer.getPurpose());
        phoneTransfer.setAccountDetailsId(200L);
        assertEquals(200L, phoneTransfer.getAccountDetailsId());
    }

    @Test
    void testBuilder() {
        PhoneTransfer transfer = PhoneTransfer.builder()
                .phoneNumber(123456789L)
                .amount(new BigDecimal("500.00"))
                .purpose("Payment")
                .accountDetailsId(300L)
                .build();
        assertNotNull(transfer);
        assertEquals(123456789L, transfer.getPhoneNumber());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals("Payment", transfer.getPurpose());
        assertEquals(300L, transfer.getAccountDetailsId());
    }

    @Test
    void testConstructor() {
        PhoneTransfer transfer = new PhoneTransfer(123456789L, new BigDecimal("500.00"), "Payment", 300L);
        assertNotNull(transfer);
        assertEquals(123456789L, transfer.getPhoneNumber());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals("Payment", transfer.getPurpose());
        assertEquals(300L, transfer.getAccountDetailsId());
    }

    @Test
    void testToString() {
        phoneTransfer.setId(100L);
        phoneTransfer.setPhoneNumber(123456789L);
        phoneTransfer.setAmount(new BigDecimal("1000.00"));
        phoneTransfer.setPurpose("Test transfer");
        phoneTransfer.setAccountDetailsId(200L);
        String expectedString = "PhoneTransfer(id=100, phoneNumber=123456789, amount=1000.00, purpose=Test transfer, accountDetailsId=200)";
        assertEquals(expectedString, phoneTransfer.toString());
    }
}
