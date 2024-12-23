package com.bank.transfer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTransferTest {
    private AccountTransfer accountTransfer;

    @BeforeEach
    public void creatNewAccountTransfer() {
        accountTransfer = new AccountTransfer();
    }

    @Test
    void testSettersAndGetters() {
        accountTransfer.setId(100L);
        assertEquals(100L, accountTransfer.getId());
        accountTransfer.setAccountNumber(123456789L);
        assertEquals(123456789L, accountTransfer.getAccountNumber());
        accountTransfer.setAmount(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1000.00"), accountTransfer.getAmount());
        accountTransfer.setPurpose("Test transfer");
        assertEquals("Test transfer", accountTransfer.getPurpose());
        accountTransfer.setAccountDetailsId(200L);
        assertEquals(200L, accountTransfer.getAccountDetailsId());
    }

    @Test
    void testBuilder() {
        AccountTransfer transfer = AccountTransfer.builder()
                .accountNumber(123456789L)
                .amount(new BigDecimal("500.00"))
                .purpose("Payment")
                .accountDetailsId(300L)
                .build();
        assertNotNull(transfer);
        assertEquals(123456789L, transfer.getAccountNumber());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals("Payment", transfer.getPurpose());
        assertEquals(300L, transfer.getAccountDetailsId());
    }

    @Test
    void testConstructor() {
        AccountTransfer transfer = new AccountTransfer(123456789L, new BigDecimal("500.00"), "Payment", 300L);
        assertNotNull(transfer);
        assertEquals(123456789L, transfer.getAccountNumber());
        assertEquals(new BigDecimal("500.00"), transfer.getAmount());
        assertEquals("Payment", transfer.getPurpose());
        assertEquals(300L, transfer.getAccountDetailsId());
    }

    @Test
    void testToString() {
        accountTransfer.setId(100L);
        accountTransfer.setAccountNumber(123456789L);
        accountTransfer.setAmount(new BigDecimal("1000.00"));
        accountTransfer.setPurpose("Test transfer");
        accountTransfer.setAccountDetailsId(200L);
        String expectedString = "AccountTransfer(id=100, accountNumber=123456789, amount=1000.00, purpose=Test transfer, accountDetailsId=200)";
        assertEquals(expectedString, accountTransfer.toString());
    }
}
