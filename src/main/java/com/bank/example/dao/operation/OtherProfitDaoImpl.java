package com.bank.example.dao.operation;

import com.bank.example.dao.AbstractDao;
import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.operation.OtherProfit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OtherProfitDaoImpl extends AbstractDao<Long, OtherProfit> implements OtherProfitDao {

    @Override
    public List<OtherProfitDto> getOtherProfitDtosByAccountId(Long accountId) {
        return entityManager.createQuery(
                "SELECT new com.bank.example.dto.OtherProfitDto(op.id, op.amount, op.dateTime, op.account.id) " +
                        "FROM OtherProfit op WHERE op.account.id=:accountId",
                OtherProfitDto.class
        )
                .setParameter("accountId", accountId)
                .getResultList();
    }
}
