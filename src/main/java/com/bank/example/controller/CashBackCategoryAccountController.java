package com.bank.example.controller;

import com.bank.example.dto.CashBackCategoryDetailedDto;
import com.bank.example.model.Account;
import com.bank.example.model.Card;
import com.bank.example.model.CashBackCategory;
import com.bank.example.service.AccountService;
import com.bank.example.service.CashBackCategoryService;
import com.bank.example.sqltracker.AssertSqlCount;
import com.bank.example.sqltracker.QueryCountInfoHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account/{accountId}/cashback/category")
public class CashBackCategoryAccountController {

    private final AccountService accountService;
    private final CashBackCategoryService cashBackCategoryService;

    public CashBackCategoryAccountController(AccountService accountService, CashBackCategoryService cashBackCategoryService) {
        this.accountService = accountService;
        this.cashBackCategoryService = cashBackCategoryService;
    }

    @GetMapping("/detailed")
    public ResponseEntity<List<CashBackCategoryDetailedDto>> getCashBackCategory() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/withCompanyAndAmount")
    public ResponseEntity<?> getCategoryDtoWithCompanyAndAmount(@PathVariable Long accountId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategoryDto(@PathVariable Long accountId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<Void> addCashBackCategory(@PathVariable Long accountId, @PathVariable Long categoryId) {

        AssertSqlCount.reset();

        Account account = accountService.getByKey(accountId);
        CashBackCategory category = cashBackCategoryService.getByKey(categoryId);
        category.addAccount(account);
        cashBackCategoryService.update(category);

        String report = QueryCountInfoHolder.getReport();
        System.out.println(report);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long accountId, @PathVariable Long categoryId) {

        AssertSqlCount.reset();

        Account account = accountService.getByKey(accountId);
        CashBackCategory category = cashBackCategoryService.getByKey(categoryId);
        category.removeAccount(account);
        cashBackCategoryService.update(category);

        String report = QueryCountInfoHolder.getReport();
        System.out.println(report);

        return ResponseEntity.ok().build();
    }
}
