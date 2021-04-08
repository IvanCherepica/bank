package com.bank.example.controller;

import com.bank.example.model.Account;
import com.bank.example.model.Deposit;
import com.bank.example.service.AccountService;
import com.bank.example.service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{accountId}/deposit")
public class DepositAccountRestController {

    private final DepositService depositService;
    private final AccountService accountService;

    public DepositAccountRestController(DepositService depositService, AccountService accountService) {
        this.depositService = depositService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createDeposit(@RequestBody Deposit deposit, @PathVariable Long accountId) {
        Account account = accountService.getByKey(accountId);
        deposit.setAccount(account);
        depositService.persist(deposit);
        account.getDeposits().add(deposit);
        accountService.update(account);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{depositId}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long depositId) {
        Deposit deposit = depositService.getByKey(depositId);
        depositService.remove(deposit);
        return ResponseEntity.ok().build();
    }
}
