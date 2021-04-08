package com.bank.example.service;

import com.bank.example.dao.DepositDao;
import com.bank.example.model.Account;
import com.bank.example.model.Card;
import com.bank.example.model.Deposit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class DepositServiceImpl extends AbstractService<Long, Deposit> implements DepositService {

    private final DepositDao depositDao;

    private final AccountService accountService;
    private final CardService cardService;

    public DepositServiceImpl(DepositDao depositDao, AccountService accountService, CardService cardService) {
        super(depositDao);
        this.depositDao = depositDao;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @Override
    @Transactional
    public void deleteOutDatedDeposit(List<Long> accountIds) {
        List<Account> accountList = accountService.getAccountsByIds(accountIds);

        for (Account account : accountList) {
            List<Deposit> depositList = account.getDeposits();
            Card card = cardService.getDefaultCardByAccountId(account.getId());

            for (Deposit deposit : depositList) {
                LocalDate closeDate = deposit.getCloseDate();
                if (DAYS.between(closeDate, LocalDate.now()) > 1) {
                    card.addSum(deposit.getSum());
                    deposit.getInterests().forEach(i -> i.setDeposit(null));
                    account.getDeposits().remove(deposit);
                    depositDao.remove(deposit);
                }
            }

            cardService.update(card);
        }
    }

    @Override
    @Transactional
    public List<Long> removeAllDepositsByAccountId(Long accountId) {
        Account account = accountService.getByKey(accountId);
        List<Long> depositIds = account.getDeposits().stream().map(Deposit::getId).collect(Collectors.toList());
        account.setDeposits(null);

        return depositIds;
    }
}
