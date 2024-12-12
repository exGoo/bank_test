package com.bank.account.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(1L)
                .passportId(2L)
                .accountNumber(3L)
                .bankDetailsId(4L)
                .money(new BigDecimal(5))
                .negativeBalance(false)
                .profileId(3323L)
                .build();
    }

    @Test
    void builderTest() {
        assertNotNull(account);
        assertEquals(1L, account.getId());
        assertEquals(2L, account.getPassportId());
        assertEquals(3L, account.getAccountNumber());
        assertEquals(4L, account.getBankDetailsId());
        assertEquals(new BigDecimal(5), account.getMoney());
        assertEquals(false, account.getNegativeBalance());
        assertEquals(3323L, account.getProfileId());
    }


    @Test
    void GetterAndSetterTest() {
        assertNotNull(account);
        assertEquals(1L, account.getId());
        assertEquals(2L, account.getPassportId());
        assertEquals(3L, account.getAccountNumber());
        assertEquals(4L, account.getBankDetailsId());
        assertEquals(new BigDecimal(5), account.getMoney());
        assertEquals(false, account.getNegativeBalance());
        assertEquals(3323L, account.getProfileId());
    }
}