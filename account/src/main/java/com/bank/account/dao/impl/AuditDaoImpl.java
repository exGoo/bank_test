package com.bank.account.dao.impl;

import com.bank.account.dao.AuditDao;
import com.bank.account.model.Audit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class AuditDaoImpl implements AuditDao {

    private final EntityManager entityManager;

    public AuditDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Audit audit) {
        entityManager.persist(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public Audit findLastAuditByUser(String createdBy) {
        String query = "SELECT a FROM Audit a WHERE a.createdBy = :createdBy ORDER BY GREATEST(a.createdAt, a.modifiedAt) DESC";
        List<Audit> results = entityManager.createQuery(query, Audit.class)
                .setParameter("createdBy", createdBy)
                .setMaxResults(1)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
