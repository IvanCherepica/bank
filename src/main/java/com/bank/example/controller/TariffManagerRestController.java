package com.bank.example.controller;

import com.bank.example.converter.TariffDtoConverter;
import com.bank.example.dto.TariffDto;
import com.bank.example.model.Tariff;
import com.bank.example.service.TariffService;
import com.bank.example.sqltracker.AssertSqlCount;
import com.bank.example.sqltracker.QueryCountInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manager/tariff")
public class TariffManagerRestController {

    @Autowired
    private TariffService tariffService;

    @PostMapping
    public ResponseEntity<TariffDto> persistEntity(@RequestBody TariffDto dto) {
        Tariff tariff = TariffDtoConverter.convertDtoWithoutAccountsToEntity(dto);
        tariff.setId(null);
        tariffService.persist(tariff);
        return ResponseEntity.ok(TariffDtoConverter.convertEntityToDto(tariff));
    }

    @PostMapping("/persistAll")
    public ResponseEntity<List<TariffDto>> persistAll(@RequestBody List<TariffDto> dtos) {
        AssertSqlCount.reset();

        List<Tariff> tariffs = dtos.stream().map(TariffDtoConverter::convertDtoWithoutAccountsToEntity).collect(Collectors.toList());
        tariffService.persistAll(tariffs);

        System.out.println(QueryCountInfoHolder.getReport());

        return ResponseEntity.ok(tariffs.stream().map(TariffDtoConverter::convertEntityToDto).collect(Collectors.toList()));
    }
}
