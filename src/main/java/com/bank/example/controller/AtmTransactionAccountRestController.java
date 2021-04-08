package com.bank.example.controller;

import com.bank.example.converter.AtmTransactionDtoConverter;
import com.bank.example.dto.AtmTransactionDto;
import com.bank.example.model.Account;
import com.bank.example.model.operation.Atm;
import com.bank.example.model.operation.AtmRefill;
import com.bank.example.model.operation.AtmTransaction;
import com.bank.example.model.operation.AtmWithdraw;
import com.bank.example.service.AccountService;
import com.bank.example.service.operation.AtmRefillService;
import com.bank.example.service.operation.AtmService;
import com.bank.example.service.operation.AtmTransactionService;
import com.bank.example.service.operation.AtmWithdrawService;
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
public class AtmTransactionAccountRestController {

    private final AtmTransactionService atmTransactionService;
    private final AtmRefillService atmRefillService;
    private final AtmWithdrawService atmWithdrawService;
    private final AtmService atmService;
    private final AccountService accountService;

    public AtmTransactionAccountRestController(AtmTransactionService atmTransactionService, AtmRefillService atmRefillService, AtmWithdrawService atmWithdrawService, AtmService atmService, AccountService accountService) {
        this.atmTransactionService = atmTransactionService;
        this.atmRefillService = atmRefillService;
        this.atmWithdrawService = atmWithdrawService;
        this.atmService = atmService;
        this.accountService = accountService;
    }

    @GetMapping("/atmTransaction")
    public ResponseEntity<List<AtmTransactionDto>> getAtmTransactionByAccountIdDto(@PathVariable Long accountId) {
        return ResponseEntity.ok(atmTransactionService.getAtmTransactionDtosByAccountId(accountId));
    }

    @GetMapping("/{transactionId}/atmTransaction/{atmTransactionId}")
    public ResponseEntity<AtmTransactionDto> getDocumentScansDto(@PathVariable Long atmTransactionId) {
        AtmTransaction atmTransaction = atmTransactionService.getByKey(atmTransactionId);
        AtmTransactionDto atmTransactionDto = AtmTransactionDtoConverter.convertEntityToDto(atmTransaction);
        return ResponseEntity.ok(atmTransactionDto);
    }

    @PostMapping("/atmTransaction/refill")
    public void addAtmRefill(@RequestBody AtmTransactionDto dto) {
        Atm atm = atmService.getByKey(dto.getAtmId());
        Account account = accountService.getByKey(dto.getAccountId());
        AtmRefill refill = new AtmRefill(dto.getAmount(), LocalDateTime.now(), account, atm);

        atmRefillService.persist(refill);
    }

    @PostMapping("/atmTransaction/withdraw")
    public void addAtmWithdraw(@RequestBody AtmTransactionDto dto) {
        Atm atm = atmService.getByKey(dto.getAtmId());
        Account account = accountService.getByKey(dto.getAccountId());
        AtmWithdraw withdraw = new AtmWithdraw(dto.getAmount(), LocalDateTime.now(), account, atm);

        atmWithdrawService.persist(withdraw);
    }
}
