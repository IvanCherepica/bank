package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.dto.TransactionDto;
import com.bank.example.model.operation.Atm;
import com.bank.example.model.operation.AtmTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AtmOperationDaoImpl extends AbstractDao<Long, AtmTransaction> implements AtmTransactionDao {

    @Override
    public List<AtmTransactionDto> getAtmTransactionDtosByAccountId(Long accountId) {
        return entityManager.createQuery(
                "SELECT new com.bank.example.dto.AtmTransactionDto(at.id, at.amount, at.dateTime, at.account.id, at.atm.id) " +
                        "FROM AtmTransaction at WHERE at.account.id=:accountId",
                AtmTransactionDto.class
        )
                .setParameter("accountId", accountId)
                .getResultList();
    }
}
