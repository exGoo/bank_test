package com.bank.account.service.impl;

import com.bank.account.dao.impl.AccountDaoImpl;
import com.bank.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountDaoImpl accountDao;

    private Account account;
    private Account accountTwo;

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

        accountTwo = Account.builder()
                .id(2L)
                .passportId(21L)
                .accountNumber(31L)
                .bankDetailsId(41L)
                .money(new BigDecimal(51))
                .negativeBalance(false)
                .profileId(33231L)
                .build();
    }

    @Test
    void save() {
        doNothing().when(accountDao).save(account);
        accountService.save(account);
        verify(accountDao, times(1)).save(account);
    }

    @Test
    void update() {
        doNothing().when(accountDao).update(account);
        accountService.update(account);
        verify(accountDao, times(1)).update(account);
    }

    @Test
    void delete() {
        doNothing().when(accountDao).delete(account);
        accountService.delete(account);
        verify(accountDao, times(1)).delete(account);
    }

    @Test
    void findById() {
        doNothing().when(accountDao).save(account);
        when(accountDao.findById(1L)).thenReturn(account);

        accountService.save(account);
        Account result = accountService.findById(1L);

        assertEquals(account, result);
    }

    @Test
    void findAll() {
        doNothing().when(accountDao).save(account);
        doNothing().when(accountDao).save(accountTwo);
        when(accountDao.findAll()).thenReturn(List.of(account, accountTwo));

        accountService.save(account);
        accountService.save(accountTwo);

        List<Account> accountsList = accountService.findAll();
        verify(accountDao, times(1)).findAll();
        assertEquals(account, accountsList.get(0));
        assertEquals(accountTwo, accountsList.get(1));
    }
}