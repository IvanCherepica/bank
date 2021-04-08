package com.bank.example.controller;

import com.bank.example.service.OperatorRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/operator/rating")
public class OperatorRatingManagerRestController {

    private final OperatorRatingService operatorRatingService;

    public OperatorRatingManagerRestController(OperatorRatingService operatorRatingService) {
        this.operatorRatingService = operatorRatingService;
    }

    @GetMapping("/top10")
    public ResponseEntity<?> getDocumentScansDto() {
        return ResponseEntity.ok().build();
    }
}
