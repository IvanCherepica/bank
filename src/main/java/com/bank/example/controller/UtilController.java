package com.bank.example.controller;

import com.bank.example.converter.ReflectionDtoConverter;
import com.bank.example.dto.reflection.ReflectionEntityDto;
import com.bank.example.model.DocumentScans;
import com.bank.example.service.DocumentScansService;
import com.bank.example.util.TransactionHolder;
import com.bank.example.util.dto.transaction.AtmTransactionUtilDto;
import com.bank.example.util.dto.transaction.OtherProfitUtilDto;
import com.bank.example.util.dto.transaction.TransactionUtilDto;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/util")
public class UtilController {

    private Environment environment;
    private final DocumentScansService documentScansService;

    public UtilController(Environment environment, DocumentScansService documentScansService) {
        this.environment = environment;
        this.documentScansService = documentScansService;
    }

    @GetMapping
    public ResponseEntity<ReflectionEntityDto> getClassByName(@RequestParam String className) {

        if (className == null) {
            return ResponseEntity.badRequest().build();
        }

        ReflectionEntityDto dto = null;
        try {
            dto = ReflectionDtoConverter.getReflectionEntityDto(Class.forName("com.bank.example.model." +className));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/scan/persist")
    public ResponseEntity<Void> persistScans() {
        try {
            DocumentScans documentScans1 = new DocumentScans();
            DocumentScans documentScans2 = DocumentScans.builder().passport("8877999000").build();

            documentScansService.persist(documentScans1);
            documentScansService.persist(documentScans2);

            documentScansService.remove(documentScans1);
            documentScansService.remove(documentScans2);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/scan/update")
    public ResponseEntity<Void> updateScans() {
        try {
            DocumentScans documentScans = DocumentScans.builder()
                    .passport("8877999000")
                    .insuranceNumber("999000")
                    .ITN("8799067444")
                    .build();

            documentScansService.persist(documentScans);

            documentScans.setITN(null);
            documentScans.setInsuranceNumber(null);

            documentScansService.update(documentScans);
            documentScansService.remove(documentScans);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/{accountId}/transaction")
    public ResponseEntity<List<TransactionUtilDto>> getTransactionByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getTransactionDtoList(accountId));
    }

    @GetMapping("/account/{accountId}/transaction/refill")
    public ResponseEntity<List<TransactionUtilDto>> getRefillTransactionByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getRefillDto(accountId));
    }

    @GetMapping("/account/{accountId}/transaction/withdraw")
    public ResponseEntity<List<TransactionUtilDto>> getWithdrawTransactionByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getWithdrawDto(accountId));
    }

    @GetMapping("/account/{accountId}/transaction/cashBack")
    public ResponseEntity<List<TransactionUtilDto>> getCashBackTransactionByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getCashBackDto(accountId));
    }

    @GetMapping("/account/{accountId}/transaction/interests")
    public ResponseEntity<List<TransactionUtilDto>> getInterestsTransactionByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getInterestsDto(accountId));
    }

    @GetMapping("/account/{accountId}/transaction/atmTransaction")
    public ResponseEntity<List<AtmTransactionUtilDto>> getAtmTransactionByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getAtmTransactionDtoList(accountId));
    }

    @GetMapping("/account/{accountId}/transaction/otherProfit")
    public ResponseEntity<List<OtherProfitUtilDto>> getOtherProfitByAccountId(@PathVariable Long accountId) {

        return ResponseEntity.ok(TransactionHolder.getOtherProfitDtoList(accountId));
    }


    @PostMapping("/properties")
    public ResponseEntity<Map<String, String>> getPropertyByName(@RequestBody String[] properties) {
        return ResponseEntity.ok(
                Arrays.stream(properties)
                        .collect(Collectors.toMap(prop -> prop, propertyName -> {
                            String propertyValue = environment.getProperty(propertyName);
                            return propertyValue == null ? "" : propertyValue;
                        }))
        );
    }

    @GetMapping("/running")
    public ResponseEntity<Void> isAppRunning() {
        return ResponseEntity.ok().build();
    }
}
