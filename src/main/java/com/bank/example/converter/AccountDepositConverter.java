package com.bank.example.converter;

import com.bank.example.dto.AccountDepositDto;

import com.bank.example.dto.DepositDto;
import com.bank.example.model.Account;
import com.bank.example.model.Deposit;

import java.util.ArrayList;
import java.util.List;

public class AccountDepositConverter {

    public static List<Account> convertListDtoToListEntity(List<AccountDepositDto> dtos) {
        List<Account> accountList = new ArrayList<>(dtos.size());

        for (AccountDepositDto dto : dtos) {
            accountList.add(convertDtoToEntity(dto));
        }

        return accountList;
    }

    public static Account convertDtoToEntity(AccountDepositDto dto) {
        Account account = new Account();
        account.setId(dto.getAccountId());
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());

        List<Deposit> depositList = getDepositList(dto);

        for (Deposit deposit : depositList) {
            deposit.setAccount(account);
        }

        account.setDeposits(depositList);

        return account;
    }

    private static List<Deposit> getDepositList(AccountDepositDto dto) {
        List<Deposit> depositList = new ArrayList<>(dto.getDeposits().size());

        for (DepositDto depositDto : dto.getDeposits()) {
            Deposit deposit = Deposit.builder()
                    .id(depositDto.getId())
                    .closeDate(depositDto.getCloseDate())
                    .openDate(depositDto.getOpenDate())
                    .rate(depositDto.getRate())
                    .sum(depositDto.getSum())
                    .build();

            depositList.add(deposit);
        }

        return depositList;
    }
}
