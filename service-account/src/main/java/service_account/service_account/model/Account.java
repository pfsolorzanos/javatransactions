package service_account.service_account.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Data
@Getter
@Setter
public class Account implements Serializable {
    @EmbeddedId
    private AccountId id;
    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false)
    private Boolean state;
    @Column(nullable = false)
    private UUID clientId;
}
