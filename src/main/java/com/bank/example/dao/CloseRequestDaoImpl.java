package com.bank.example.dao;

import com.bank.example.model.Account;
import com.bank.example.model.CloseRequest;
import com.bank.example.model.DocumentScans;
import org.springframework.stereotype.Repository;

@Repository
public class CloseRequestDaoImpl extends AbstractDao<Long, CloseRequest> implements CloseRequestDao {
    @Override
    public CloseRequest getCloseRequestByAccountId(Long accountId) {
        return entityManager.createQuery(
                "SELECT c FROM CloseRequest c WHERE c.account.id = :accountId",
                CloseRequest.class
        )
                .setParameter("accountId", accountId)
                .getSingleResult();
    }
}
