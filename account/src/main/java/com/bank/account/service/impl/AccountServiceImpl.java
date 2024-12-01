package com.bank.account.service.impl;

import com.bank.account.dao.AccountDao;
import com.bank.account.model.Account;
import com.bank.account.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Класс AccountServiceImpl является реализацией
 * интерфейса AccountService и отвечает за предоставление
 * сервисного слоя для работы с объектами типа Account.
 * */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    public AccountServiceImpl(AccountDao accountDao) { this.accountDao = accountDao; }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Account account) {
        accountDao.delete(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountDao.findAll();
    }
}
