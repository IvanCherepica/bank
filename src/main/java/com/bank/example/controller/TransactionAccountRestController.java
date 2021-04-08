package com.bank.example.controller;

import com.bank.example.converter.TransactionDtoConverter;
import com.bank.example.dto.TransactionDto;
import com.bank.example.model.operation.Transaction;

import com.bank.example.service.operation.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account/{accountId}/transaction")
public class TransactionAccountRestController {

    private final TransactionService transactionService;

    public TransactionAccountRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactionDtoByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getAllTransactionDtoByAccountId(accountId));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionDto(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.getByKey(transactionId);
        TransactionDto transactionDto = TransactionDtoConverter.convertEntityToDto(transaction);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping("/refill")
    public ResponseEntity<List<TransactionDto>> getRefillTransactionDto(@PathVariable Long accountId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @GetMapping("/withdraw")
    public ResponseEntity<List<TransactionDto>> getWithdrawTransactionDto(@PathVariable Long accountId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cashBack")
    public ResponseEntity<List<TransactionDto>> getCashBackTransactionDto(@PathVariable Long accountId) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @GetMapping("/interests")
    public ResponseEntity<List<TransactionDto>> getInterestsTransactionDto(@PathVariable Long accountId) {
        //TODO
        return ResponseEntity.ok().build();
    }
}
