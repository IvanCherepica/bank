package com.bank.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isNotificationAllowed = true;

    private Boolean isPayInStoresAllowed = true;

    private Boolean isPayOnlineAllowed = true;

    private Boolean isCashWithdrawalAllowed = true;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    public Settings() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settings settings = (Settings) o;
        return Objects.equals(id, settings.id) &&
                Objects.equals(isNotificationAllowed, settings.isNotificationAllowed) &&
                Objects.equals(isPayInStoresAllowed, settings.isPayInStoresAllowed) &&
                Objects.equals(isPayOnlineAllowed, settings.isPayOnlineAllowed) &&
                Objects.equals(isCashWithdrawalAllowed, settings.isCashWithdrawalAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isNotificationAllowed, isPayInStoresAllowed, isPayOnlineAllowed, isCashWithdrawalAllowed);
    }
}
