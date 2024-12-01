package com.bank.account.dao.impl;

import com.bank.account.dao.AccountDao;
import com.bank.account.model.Account;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * Класс AccountServiceImpl является реализацией интерфейса AccountService
 * и отвечает за предоставление сервисного слоя для работы с объектами типа Account
 * */
@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    private final EntityManager entityManager;

    /**
     * В этом конструкторе передаётся объект EntityManager, который используется для взаимодействия с базой данных.
     * */
    public AccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Это метод для сохранения объекта типа Account в БД
     * @param account Принимается созданный объект
     * */
    @Override
    public void save(Account account) {
        entityManager.persist(account);
    }

    /**
     * Этот метод для обновления данных (полей) объекта типа Account в БД
     * @param account Принимается объект с измененными полями,
     *                который вы ранее нашли в БД через метод findById(Long id)
     * */
    @Override
    public void update(Account account) {
        entityManager.merge(account);
    }

    /**
     * Этот метод для удаления объекта типа Account из БД
     * @param account Принимается подлежащий удалению из БД объект,
     *                который вы ранее нашли в БД через метод findById(Long id)
     * */
    @Override
    public void delete(Account account) {
        entityManager.remove(account);
    }

    /**
     * Этот метод для поиска и выдачи объекта типа Account из БД
     * @param id Принимается ID объекта
     * @return Возвращает найденный объект из БД
     * */
    @Override
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    /**
     * Этот метод для удаления объекта типа Account из БД
     * @return Возвращает список всех существующих аккаунтов из БД
     * */
    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return entityManager.createQuery("from Account", Account.class).getResultList();
    }
}
