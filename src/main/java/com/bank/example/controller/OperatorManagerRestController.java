package com.bank.example.controller;

import com.bank.example.converter.ManagerDtoConverter;
import com.bank.example.converter.OperatorDtoConverter;
import com.bank.example.dto.ManagerDto;
import com.bank.example.dto.OperatorDto;
import com.bank.example.model.Operator;
import com.bank.example.service.OperatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager/operator")
public class OperatorManagerRestController {

    private final OperatorService operatorService;

    public OperatorManagerRestController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Operator>> getAll() {
        return ResponseEntity.ok(operatorService.getAll());
    }

    @GetMapping("/{operatorId}")
    public ResponseEntity<Operator> getById(@PathVariable Long operatorId) {
        return ResponseEntity.ok(operatorService.getByKey(operatorId));
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody OperatorDto dto) {
        Operator operator = OperatorDtoConverter.convertDtoToEntity(dto);
        operatorService.persist(operator);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody OperatorDto dto) {
        Operator operator = OperatorDtoConverter.convertDtoToEntity(dto);
        operatorService.update(operator);
        return ResponseEntity.ok().build();
    }
}
