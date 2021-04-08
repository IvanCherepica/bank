package com.bank.example.controller;

import com.bank.example.model.Account;
import com.bank.example.service.AccountService;
import com.bank.example.service.DepositService;
import com.bank.example.sqltracker.AssertSqlCount;
import com.bank.example.sqltracker.QueryCountInfoHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/manager/deposit")
public class DepositManagerRestController {

    private final DepositService depositService;

    public DepositManagerRestController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PatchMapping("/deleteOutDated")
    public ResponseEntity<Void> deleteOutDatedDeposits(@RequestBody Long[] accountIds) {
        AssertSqlCount.reset();
        depositService.deleteOutDatedDeposit(Arrays.asList(accountIds));
        System.out.println(QueryCountInfoHolder.getReport());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deleteAll")
    public ResponseEntity<List<Long>> deleteAllDeposits(@RequestBody Long accountId) {
        return ResponseEntity.ok(depositService.removeAllDepositsByAccountId(accountId));
    }
}
