package com.bank.example.service;

import com.bank.example.model.Deposit;

import java.util.List;

public interface DepositService extends GenericService<Long, Deposit> {

//    List<Deposit> getAllDepositsByAccountId(Long accountId);

    void deleteOutDatedDeposit(List<Long> accountIds);

    List<Long> removeAllDepositsByAccountId(Long accountId);
}
