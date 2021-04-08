package com.bank.example.controller;

import com.bank.example.model.CloseRequest;
import com.bank.example.service.CloseRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/closeRequest")
public class CloseRequestManagerRestController {

    private final CloseRequestService closeRequestService;

    public CloseRequestManagerRestController(CloseRequestService closeRequestService) {
        this.closeRequestService = closeRequestService;
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<CloseRequest> getCloseRequestByUserId(@PathVariable Long accountId) {
        CloseRequest closeRequest = closeRequestService.getCloseRequestByAccountId(accountId);
        return ResponseEntity.ok(closeRequest);
    }
}
