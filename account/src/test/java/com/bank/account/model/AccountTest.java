package com.bank.account.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTest {

    @Test
    void builderTest() {
        Account account = Account.builder()
                .id(1L)
                .passportId(2L)
                .accountNumber(3L)
                .bankDetailsId(4L)
                .money(new BigDecimal(5))
                .negativeBalance(false)
                .profileId(3323L)
                .build();

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
        Account account = new Account();

        account.setId(1L);
        account.setPassportId(2L);
        account.setAccountNumber(3L);
        account.setBankDetailsId(4L);
        account.setMoney(new BigDecimal(5));
        account.setNegativeBalance(false);
        account.setProfileId(3323L);

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