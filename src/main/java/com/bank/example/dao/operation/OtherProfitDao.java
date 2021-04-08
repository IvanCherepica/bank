package com.bank.example.dao.operation;

import com.bank.example.dao.GenericDao;
import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.operation.OtherProfit;

import java.util.List;

public interface OtherProfitDao extends GenericDao<Long, OtherProfit> {

    List<OtherProfitDto> getOtherProfitDtosByAccountId(Long accountId);
}
