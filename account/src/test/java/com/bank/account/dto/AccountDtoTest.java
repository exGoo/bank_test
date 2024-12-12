package com.bank.account.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountDtoTest {

    @Test
    void AccountDtoTestCreation() {
        AccountDto accountDto = AccountDto.builder()
                        .id(1L)
                        .passportId(2L)
                        .accountNumber(3L)
                        .bankDetailsId(4L)
                        .money(new BigDecimal(5L))
                        .negativeBalance(false)
                        .profileId(6L)
                        .build();

        assertNotNull(accountDto);
        assertEquals(1L, accountDto.getId());
        assertEquals(2L, accountDto.getPassportId());
        assertEquals(3L, accountDto.getAccountNumber());
        assertEquals(4L, accountDto.getBankDetailsId());
        assertEquals(BigDecimal.valueOf(5L), accountDto.getMoney());
        assertEquals(false, accountDto.getNegativeBalance());
        assertEquals(6L, accountDto.getProfileId());
    }

    @Test
    void AccountDtoTestSetterAndGetter() {
        AccountDto accountDto = AccountDto.builder()
                .id(99L)
                .passportId(98L)
                .accountNumber(97L)
                .bankDetailsId(96L)
                .money(new BigDecimal(95L))
                .negativeBalance(true)
                .profileId(94L)
                .build();

        accountDto.setId(1L);
        accountDto.setPassportId(2L);
        accountDto.setAccountNumber(3L);
        accountDto.setBankDetailsId(4L);
        accountDto.setMoney(new BigDecimal(5L));
        accountDto.setNegativeBalance(false);
        accountDto.setProfileId(6L);

        assertNotNull(accountDto);

        assertEquals(1L, accountDto.getId());
        assertEquals(2L, accountDto.getPassportId());
        assertEquals(3L, accountDto.getAccountNumber());
        assertEquals(4L, accountDto.getBankDetailsId());
        assertEquals(BigDecimal.valueOf(5L), accountDto.getMoney());
        assertEquals(false, accountDto.getNegativeBalance());
        assertEquals(6L, accountDto.getProfileId());
    }
}