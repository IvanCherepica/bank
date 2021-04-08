package com.bank.example.controller;

import com.bank.example.model.Account;
import com.bank.example.model.Card;
import com.bank.example.model.Deposit;
import com.bank.example.service.AccountService;
import com.bank.example.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{accountId}/card")
public class CardAccountRestController {

    private final CardService cardService;
    private final AccountService accountService;

    public CardAccountRestController(CardService cardService, AccountService accountService) {
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createDeposit(@RequestBody Card card, @PathVariable Long accountId) {
        Account account = accountService.getByKey(accountId);
        card.setAccount(account);
        cardService.persist(card);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long cardId) {
        Card card = cardService.getByKey(cardId);
        cardService.remove(card);
        return ResponseEntity.ok().build();
    }
}
