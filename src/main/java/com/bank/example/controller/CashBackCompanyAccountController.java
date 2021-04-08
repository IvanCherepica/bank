package com.bank.example.controller;

import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import com.bank.example.service.AccountService;
import com.bank.example.service.CashBackCompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{accountId}/cashback/company")
public class CashBackCompanyAccountController {

    private final AccountService accountService;
    private final CashBackCompanyService cashBackCompanyService;

    public CashBackCompanyAccountController(AccountService accountService, CashBackCompanyService cashBackCompanyService) {
        this.accountService = accountService;
        this.cashBackCompanyService = cashBackCompanyService;
    }

    @PostMapping("/{companyId}")
    public ResponseEntity<Void> addCashBackCategory(@PathVariable Long accountId, @PathVariable Long companyId) {
        Account account = accountService.getByKey(accountId);
        CashBackCompany company = cashBackCompanyService.getByKey(companyId);
        company.addAccount(account);
        cashBackCompanyService.update(company);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long accountId, @PathVariable Long companyId) {
        Account account = accountService.getByKey(accountId);
        CashBackCompany company = cashBackCompanyService.getByKey(companyId);
        company.removeAccount(account);
        cashBackCompanyService.update(company);

        return ResponseEntity.ok().build();
    }
}
