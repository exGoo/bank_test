package com.bank.account.service.impl;

import com.bank.account.dao.AccountDao;
import com.bank.account.model.Account;
import com.bank.account.service.AccountService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) { this.accountDao = accountDao; }

    @Override
    @Transactional
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    @Transactional
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    @Transactional
    public void delete(Account account) {
        accountDao.delete(account);
    }

    @Override
    @Transactional
    public Account findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return accountDao.findAll();
    }
}