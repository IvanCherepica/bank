package com.bank.example.service;

import com.bank.example.dao.DocumentScansDao;
import com.bank.example.model.DocumentScans;
import org.springframework.stereotype.Service;

@Service
public class DocumentScansServiceImpl extends AbstractService<Long, DocumentScans> implements DocumentScansService {

    private final DocumentScansDao documentScansDao;

    public DocumentScansServiceImpl(DocumentScansDao documentScansDao) {
        super(documentScansDao);
        this.documentScansDao = documentScansDao;
    }

    @Override
    public DocumentScans getDocumentScansByAccountId(Long accountId) {
        return documentScansDao.getDocumentScansByAccountId(accountId);
    }
}
