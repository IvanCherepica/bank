package com.bank.example.service;

import com.bank.example.dao.AccountDao;
import com.bank.example.dto.AccountInfoDto;
import com.bank.example.dto.AccountSumDto;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AccountServiceImpl extends AbstractService<Long, Account> implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        super(accountDao);
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> getAccountsByIds(List<Long> accountIds) {
        return accountDao.getAccountsByIds(accountIds);
    }

    @Override
    public List<AccountSumDto> getTopAccountListBySumOnDeposit() {
        return accountDao.getTopAccountListBySumOnDeposit();
    }

    @Override
    @Transactional
    public void remove(Account account) {
        Set<CashBackCategory> cashBackCategories = new HashSet<>(account.getCashBackCategories());
        for (CashBackCategory category : cashBackCategories) {
            category.removeAccount(account);
        }

        Set<CashBackCompany> cashBackCompanies = new HashSet<>(account.getCashBackCompanies());
        for (CashBackCompany company : cashBackCompanies) {
            company.removeAccount(account);
        }

        super.remove(account);
    }
}
