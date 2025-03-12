package service_account.service_account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import service_account.service_account.utils.AccountType;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class AccountId implements Serializable {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Column(nullable = false)
    private String number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId that = (AccountId) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(number, that.number);
    }
}
