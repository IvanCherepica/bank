package com.bank.example.controller;

import com.bank.example.model.Employee;
import com.bank.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager/employee")
public class EmployeeManagerRestController {

    private final EmployeeService employeeService;

    public EmployeeManagerRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getByKey(employeeId));
    }
}
