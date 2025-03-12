package service_account.service_account.utils.dto.bankStatement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTO {
    private String type;
    private String number;
    private BigDecimal balance;
    List<MovementTO> movements;
}
