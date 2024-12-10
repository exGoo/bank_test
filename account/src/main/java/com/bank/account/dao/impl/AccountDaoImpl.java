package com.bank.account.dao.impl;

import com.bank.account.dao.AccountDao;
import com.bank.account.model.Account;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    private final EntityManager entityManager;

    public AccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Account account) {
        entityManager.persist(account);
    }

    @Override
    @Transactional
    public void update(Account account) {
        entityManager.merge(account);
    }

    @Override
    @Transactional
    public void delete(Account account) {
        entityManager.remove(account);
    }

    @Override
    @Transactional
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return entityManager.createQuery("from Account", Account.class).getResultList();
    }
}
