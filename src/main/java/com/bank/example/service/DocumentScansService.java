package com.bank.example.service;

import com.bank.example.model.DocumentScans;

public interface DocumentScansService extends GenericService<Long, DocumentScans> {

    DocumentScans getDocumentScansByAccountId(Long accountId);
}
