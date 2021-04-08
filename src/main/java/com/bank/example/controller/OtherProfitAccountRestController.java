package com.bank.example.controller;

import com.bank.example.converter.OtherProfitDtoConverter;
import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.dto.CashBackDto;
import com.bank.example.dto.InterestsDto;
import com.bank.example.dto.OtherProfitDto;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCompany;
import com.bank.example.model.Deposit;
import com.bank.example.model.operation.AtmWithdraw;
import com.bank.example.model.operation.CashBack;
import com.bank.example.model.operation.Interests;
import com.bank.example.model.operation.OtherProfit;
import com.bank.example.service.AccountService;
import com.bank.example.service.CashBackCompanyService;
import com.bank.example.service.DepositService;
import com.bank.example.service.operation.CashBackService;
import com.bank.example.service.operation.InterestsService;
import com.bank.example.service.operation.OtherProfitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/account/{accountId}/transaction")
public class OtherProfitAccountRestController {

    private final OtherProfitService otherProfitService;
    private final CashBackService cashBackService;
    private final InterestsService interestsService;
    private final AccountService accountService;
    private final DepositService depositService;
    private final CashBackCompanyService cashBackCompanyService;

    public OtherProfitAccountRestController(OtherProfitService otherProfitService, CashBackService cashBackService, InterestsService interestsService, AccountService accountService, DepositService depositService, CashBackCompanyService cashBackCompanyService) {
        this.otherProfitService = otherProfitService;
        this.cashBackService = cashBackService;
        this.interestsService = interestsService;
        this.accountService = accountService;
        this.depositService = depositService;
        this.cashBackCompanyService = cashBackCompanyService;
    }

    @GetMapping("/otherProfit")
    public ResponseEntity<List<OtherProfitDto>> getAtmTransactionByAccountIdDto(@PathVariable Long accountId) {
        return ResponseEntity.ok(otherProfitService.getOtherProfitDtosByAccountId(accountId));
    }

    @GetMapping("/{transactionId}/otherProfit/{profitId}")
    public ResponseEntity<OtherProfitDto> getDocumentScansDto(@PathVariable Long profitId) {
        OtherProfit otherProfit = otherProfitService.getByKey(profitId);
        OtherProfitDto otherProfitDto = OtherProfitDtoConverter.convertEntityToDto(otherProfit);
        return ResponseEntity.ok(otherProfitDto);
    }

    @PostMapping("/otherProfit/interests")
    public void addInterests(@RequestBody InterestsDto dto) {
        Account account = accountService.getByKey(dto.getAccountId());
        Deposit deposit = depositService.getByKey(dto.getDepositId());
        Interests interests = new Interests(dto.getAmount(), LocalDateTime.now(), account, deposit);

        interestsService.persist(interests);
    }

    @PostMapping("/otherProfit/cashBack")
    public void addCashBack(@RequestBody CashBackDto dto) {
        Account account = accountService.getByKey(dto.getAccountId());
        CashBackCompany cashBackCompany = cashBackCompanyService.getByKey(dto.getCashBackCompanyId());
        CashBack cashBack = new CashBack(dto.getAmount(), LocalDateTime.now(), account, cashBackCompany);

        cashBackService.persist(cashBack);
    }
}
