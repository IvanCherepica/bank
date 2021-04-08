package com.bank.example.dao;

import com.bank.example.model.DocumentScans;

public interface DocumentScansDao extends GenericDao<Long, DocumentScans> {

    DocumentScans getDocumentScansByAccountId(Long accountId);
}
