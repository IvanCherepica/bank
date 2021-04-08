package com.bank.example.controller;

import com.bank.example.dto.AccountInfoDto;
import com.bank.example.dto.SettingsDto;
import com.bank.example.model.Account;
import com.bank.example.model.Settings;
import com.bank.example.service.SettingsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{accountId}/settings")
public class SettingsRestController {

    private final SettingsService settingsService;

    private final ModelMapper modelMapper;

    public SettingsRestController(SettingsService settingsService, ModelMapper modelMapper) {
        this.settingsService = settingsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    private ResponseEntity<SettingsDto> getSettingsDto(@PathVariable Long accountId) {
        Settings settings = settingsService.getSettingsByAccountId(accountId);
        SettingsDto settingsDto = modelMapper.map(settings, SettingsDto.class);
        return ResponseEntity.ok(settingsDto);
    }
}
