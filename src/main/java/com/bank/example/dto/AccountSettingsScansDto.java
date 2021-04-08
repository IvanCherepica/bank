package com.bank.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccountSettingsScansDto {

    private Long accountId;

    private String firstName;

    private String lastName;

    private Long settingsId;

    @JsonProperty
    private boolean isNotificationAllowed;

    @JsonProperty
    private boolean isPayInStoresAllowed;

    @JsonProperty
    private boolean isPayOnlineAllowed;

    @JsonProperty
    private boolean isCashWithdrawalAllowed;

    private Long documentScansId;

    private String passport;

    private String ITN;

    private String insuranceNumber;


}
