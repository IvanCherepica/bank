package com.bank.example.service.operation;

import com.bank.example.dao.operation.OtherProfitDao;
import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.operation.OtherProfit;
import com.bank.example.service.AbstractService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherProfitServiceImpl extends AbstractService<Long, OtherProfit> implements OtherProfitService {

    private final OtherProfitDao otherProfitDao;

    public OtherProfitServiceImpl(OtherProfitDao otherProfitDao) {
        super(otherProfitDao);
        this.otherProfitDao = otherProfitDao;
    }

    @Override
    public List<OtherProfitDto> getOtherProfitDtosByAccountId(Long accountId) {
        return otherProfitDao.getOtherProfitDtosByAccountId(accountId);
    }
}
