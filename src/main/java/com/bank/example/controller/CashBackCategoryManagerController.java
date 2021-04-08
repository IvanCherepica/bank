package com.bank.example.controller;

import com.bank.example.dto.CashBackCategoryDetailedDto;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.service.CashBackCategoryService;
import com.bank.example.sqltracker.AssertSqlCount;
import com.bank.example.sqltracker.QueryCountInfoHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager/cashback/category")
public class CashBackCategoryManagerController {

    private final CashBackCategoryService cashBackCategoryService;

    public CashBackCategoryManagerController(CashBackCategoryService cashBackCategoryService) {
        this.cashBackCategoryService = cashBackCategoryService;
    }

    @GetMapping("/detailed")
    public ResponseEntity<List<CashBackCategoryDetailedDto>> getCashBackCategory() {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long categoryId) {
        CashBackCategory category = cashBackCategoryService.getByKey(categoryId);
        cashBackCategoryService.remove(category);
        return ResponseEntity.ok().build();
    }
}
