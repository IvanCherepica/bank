package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.dto.TransactionDto;
import com.bank.example.model.operation.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationDaoImpl extends AbstractDao<Long, Transaction> implements TransactionDao {

    @Override
    public List<TransactionDto> getAllTransactionDtoByAccountId(Long accountId) {
        return entityManager.createQuery(
                "SELECT new com.bank.example.dto.TransactionDto(t.id, t.amount, t.dateTime, t.account.id) " +
                        "FROM Transaction t WHERE t.account.id=:accountId",
                TransactionDto.class
        )
                .setParameter("accountId", accountId)
                .getResultList();
    }
}
