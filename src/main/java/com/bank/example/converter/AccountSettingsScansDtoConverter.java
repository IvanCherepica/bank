package com.bank.example.converter;

import com.bank.example.dto.AccountSettingsScansDto;
import com.bank.example.model.Account;
import com.bank.example.model.DocumentScans;
import com.bank.example.model.Settings;


public class AccountSettingsScansDtoConverter {

    public static AccountSettingsScansDto convertToDto(Account account, Settings settings, DocumentScans documentScans) {
        return AccountSettingsScansDto.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .isCashWithdrawalAllowed(settings.getIsCashWithdrawalAllowed())
                .isNotificationAllowed(settings.getIsNotificationAllowed())
                .isPayInStoresAllowed(settings.getIsPayInStoresAllowed())
                .isPayOnlineAllowed(settings.getIsPayOnlineAllowed())
                .insuranceNumber(documentScans.getInsuranceNumber())
                .passport(documentScans.getPassport())
                .ITN(documentScans.getITN())
                .build();
    }

    public static Account getAccount(AccountSettingsScansDto dto) {
        return Account.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

    }

    public static Settings getSettings(AccountSettingsScansDto dto) {
        return Settings.builder()
                .isPayOnlineAllowed(dto.isPayOnlineAllowed())
                .isPayInStoresAllowed(dto.isPayInStoresAllowed())
                .isNotificationAllowed(dto.isNotificationAllowed())
                .isCashWithdrawalAllowed(dto.isCashWithdrawalAllowed())
                .build();
    }

    public static DocumentScans getDocumentScans(AccountSettingsScansDto dto) {
        return DocumentScans.builder()
                .passport(dto.getPassport())
                .ITN(dto.getITN())
                .insuranceNumber(dto.getInsuranceNumber())
                .build();
    }
}
