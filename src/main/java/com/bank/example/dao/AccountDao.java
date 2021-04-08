package com.bank.example.dao;

import com.bank.example.dto.AccountInfoDto;
import com.bank.example.dto.AccountSumDto;
import com.bank.example.model.Account;

import java.util.List;

public interface AccountDao extends GenericDao<Long, Account> {

    List<Account> getAccountsByIds(List<Long> accountIds);

    List<AccountSumDto> getTopAccountListBySumOnDeposit();
}
