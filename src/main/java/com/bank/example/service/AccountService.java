package com.bank.example.service;

import com.bank.example.dto.AccountInfoDto;
import com.bank.example.dto.AccountSumDto;
import com.bank.example.model.Account;

import java.util.List;

public interface AccountService extends GenericService<Long, Account> {

    List<Account> getAccountsByIds(List<Long> accountIds);

    List<AccountSumDto> getTopAccountListBySumOnDeposit();
}
