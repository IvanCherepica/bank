package com.bank.example.controller;

import com.bank.example.converter.AccountDepositConverter;
import com.bank.example.converter.AccountSettingsScansDtoConverter;
import com.bank.example.dto.AccountDepositDto;
import com.bank.example.dto.AccountSettingsScansDto;
import com.bank.example.dto.AccountSumDto;
import com.bank.example.model.Account;
import com.bank.example.model.DocumentScans;
import com.bank.example.model.Settings;
import com.bank.example.service.AccountService;
import com.bank.example.service.DocumentScansService;
import com.bank.example.service.SettingsService;
import com.bank.example.sqltracker.AssertSqlCount;
import com.bank.example.sqltracker.QueryCountInfoHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager/account")
public class AccountManagerRestController {

    private final AccountService accountService;
    private final DocumentScansService documentScansService;
    private final SettingsService settingsService;

    public AccountManagerRestController(AccountService accountService, DocumentScansService documentScansService, SettingsService settingsService) {
        this.accountService = accountService;
        this.documentScansService = documentScansService;
        this.settingsService = settingsService;
    }

    @GetMapping("/amount")
    public ResponseEntity<List<AccountSumDto>> getAccountAmountStatistic() {
        List<AccountSumDto> accountInfoDto = accountService.getTopAccountListBySumOnDeposit();
        return ResponseEntity.ok(accountInfoDto);
    }

    @PostMapping("/persist")
    public ResponseEntity<Void> persist(@RequestBody AccountSettingsScansDto dto) {
        Account account = AccountSettingsScansDtoConverter.getAccount(dto);
        DocumentScans documentScans = AccountSettingsScansDtoConverter.getDocumentScans(dto);
        Settings settings = AccountSettingsScansDtoConverter.getSettings(dto);

        accountService.persist(account);
        documentScans.setAccount(account);
        settings.setAccount(account);
        documentScansService.persist(documentScans);
        settingsService.persist(settings);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/persistAll")
    public ResponseEntity<Void> persistAll(@RequestBody List<AccountDepositDto> accountSettingsScansDtoList) {
        List<Account> accountList = AccountDepositConverter.convertListDtoToListEntity(accountSettingsScansDtoList);

        AssertSqlCount.reset();

        accountService.persistAll(accountList);

        System.out.println(QueryCountInfoHolder.getReport());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateAll")
    public ResponseEntity<Void> updateAll(@RequestBody List<AccountDepositDto> accountSettingsScansDtoList) {
        List<Account> accountList = AccountDepositConverter.convertListDtoToListEntity(accountSettingsScansDtoList);

        AssertSqlCount.reset();

        accountService.updateAll(accountList);

        System.out.println(QueryCountInfoHolder.getReport());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable Long accountId) {
        Account account = accountService.getByKey(accountId);
        accountService.remove(account);
        return ResponseEntity.ok().build();
    }
}
