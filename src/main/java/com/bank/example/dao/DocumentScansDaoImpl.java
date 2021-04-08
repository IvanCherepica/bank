package com.bank.example.dao;

import com.bank.example.model.DocumentScans;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentScansDaoImpl extends AbstractDao<Long, DocumentScans> implements DocumentScansDao {
    @Override
    public DocumentScans getDocumentScansByAccountId(Long accountId) {
        return entityManager.createQuery(
                "SELECT d FROM DocumentScans d WHERE d.account.id = :accountId",
                DocumentScans.class
        )
                .setParameter("accountId", accountId)
                .getSingleResult();
    }
}
