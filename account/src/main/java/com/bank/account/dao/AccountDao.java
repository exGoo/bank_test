package com.bank.account.dao;

import com.bank.account.model.Account;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountDao {

    void save(Account account);

    void update(Account account);

    void delete(Account account);

    Account findById(Long id);

    List<Account> findAll();
}
