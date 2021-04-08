package com.bank.example.service.operation;

import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.operation.OtherProfit;
import com.bank.example.service.GenericService;

import java.util.List;

public interface OtherProfitService extends GenericService<Long, OtherProfit> {

    List<OtherProfitDto> getOtherProfitDtosByAccountId(Long accountId);
}
