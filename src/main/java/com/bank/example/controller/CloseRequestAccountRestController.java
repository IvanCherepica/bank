package com.bank.example.controller;

import com.bank.example.model.Account;
import com.bank.example.model.CloseRequest;
import com.bank.example.service.AccountService;
import com.bank.example.service.CloseRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{accountId}/closeRequest")
public class CloseRequestAccountRestController {

    private final CloseRequestService closeRequestService;
    private final AccountService accountService;

    public CloseRequestAccountRestController(CloseRequestService closeRequestService, AccountService accountService) {
        this.closeRequestService = closeRequestService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> getCloseRequestByUserId(@RequestBody CloseRequest closeRequest, @PathVariable Long accountId) {
        Account account = accountService.getByKey(accountId);
        closeRequest.setAccount(account);
        closeRequestService.persist(closeRequest);
        return ResponseEntity.ok().build();
    }
}
