package com.bank.example.controller;

import com.bank.example.converter.ManagerDtoConverter;
import com.bank.example.dto.ManagerDto;
import com.bank.example.model.Employee;
import com.bank.example.model.Manager;
import com.bank.example.service.ManagerService;
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
@RequestMapping("/api/manager")
public class ManagerRestController {

    private final ManagerService managerService;

    public ManagerRestController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Manager>> getAll() {
        return ResponseEntity.ok(managerService.getAll());
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<Manager> getById(@PathVariable Long managerId) {
        return ResponseEntity.ok(managerService.getByKey(managerId));
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody ManagerDto dto) {
        Manager manager = ManagerDtoConverter.convertDtoToEntity(dto);
        managerService.persist(manager);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ManagerDto dto) {
        Manager manager = ManagerDtoConverter.convertDtoToEntity(dto);
        managerService.update(manager);
        return ResponseEntity.ok().build();
    }
}
