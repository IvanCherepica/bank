package com.bank.example.converter;

import com.bank.example.dto.TariffDto;
import com.bank.example.model.Account;
import com.bank.example.model.Tariff;

import java.util.stream.Collectors;

public class TariffDtoConverter {

    public static Tariff convertDtoWithoutAccountsToEntity(TariffDto dto) {
        Tariff tariff = new Tariff();
        tariff.setName(dto.getName());
        tariff.setId(dto.getId());
        return tariff;
    }

    public static TariffDto convertEntityToDto(Tariff entity) {
        TariffDto tariffDto = new TariffDto();
        tariffDto.setId(entity.getId());
        tariffDto.setName(entity.getName());
        tariffDto.setAccountIds(entity.getAccounts().stream().map(Account::getId).collect(Collectors.toList()));
        return tariffDto;
    }
}
