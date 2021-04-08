package com.bank.example.service.operation;

import com.bank.example.dao.operation.AtmTransactionDao;
import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.model.operation.AtmTransaction;
import com.bank.example.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtmOperationServiceImpl extends AbstractService<Long, AtmTransaction> implements AtmTransactionService {

    private final AtmTransactionDao atmOperationDao;

    public AtmOperationServiceImpl(AtmTransactionDao atmOperationDao) {
        super(atmOperationDao);
        this.atmOperationDao = atmOperationDao;
    }

    @Override
    public List<AtmTransactionDto> getAtmTransactionDtosByAccountId(Long accountId) {
        return atmOperationDao.getAtmTransactionDtosByAccountId(accountId);
    }
}
