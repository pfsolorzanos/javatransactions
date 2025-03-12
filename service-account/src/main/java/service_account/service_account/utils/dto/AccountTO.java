package service_account.service_account.utils.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTO {
    private String type;
    private String number;
    private BigDecimal balance;
    private Boolean state;
    private String clientId;
}
