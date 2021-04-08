package com.bank.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SettingsDto {

    private long id;

    private boolean isNotificationAllowed;

    private boolean isPayInStoresAllowed;

    private boolean isPayOnlineAllowed;

    private boolean isCashWithdrawalAllowed;
}
