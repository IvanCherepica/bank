package com.bank.example.controller;

import com.bank.example.dto.AccountInfoDto;
import com.bank.example.dto.DocumentScansDto;
import com.bank.example.model.DocumentScans;
import com.bank.example.service.DocumentScansService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{accountId}/documentScans")
public class DocumentScansRestController {

    private final DocumentScansService documentScansService;

    private final ModelMapper modelMapper;

    public DocumentScansRestController(DocumentScansService documentScansService, ModelMapper modelMapper) {
        this.documentScansService = documentScansService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<DocumentScansDto> getDocumentScansDto(@PathVariable Long accountId) {
        DocumentScans documentScans = documentScansService.getDocumentScansByAccountId(accountId);
        DocumentScansDto documentScansDto = modelMapper.map(documentScans, DocumentScansDto.class);
        return ResponseEntity.ok(documentScansDto);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody DocumentScansDto dto) {
        DocumentScans documentScans = modelMapper.map(dto, DocumentScans.class);
        documentScansService.update(documentScans);

        return ResponseEntity.ok().build();
    }
}
