package service_account.service_account.utils.dto.bankStatement;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ClientTO {
    private String id;
    private String name;
    List<AccountTO> accounts;
}
