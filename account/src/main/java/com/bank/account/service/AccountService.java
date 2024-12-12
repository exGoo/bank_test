package com.bank.account.service;

import com.bank.account.model.Account;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface AccountService {

    void save(Account account);

    void update(Account account);

    void delete(Account account);

    Account findById(Long id);

    List<Account> findAll();
}
