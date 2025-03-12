package service_account.service_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service_account.service_account.feign.ClientFeignClient;
import service_account.service_account.model.Account;
import service_account.service_account.model.Movement;
import service_account.service_account.repository.MovementRepository;
import service_account.service_account.utils.dto.bankStatement.AccountTO;
import service_account.service_account.utils.dto.bankStatement.ClientTO;
import service_account.service_account.utils.dto.bankStatement.MovementTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankStatementService {
    @Autowired
    private ClientFeignClient clientFeignClient;
    @Autowired
    private MovementRepository movementRepository;

    public ClientTO getStatementByDateRange(LocalDate dateFrom, LocalDate dateTo, UUID clientId) {

        List<Movement> movementsByClientIdAndDateRange = movementRepository.findMovementsByClientIdAndDateRange(dateFrom, dateTo, clientId);

        Map<Account, List < Movement >> movementsByAccount =
                movementsByClientIdAndDateRange.stream().collect(Collectors.groupingBy(
                                Movement::getAccount
                        ));

        ClientTO clientTO = ClientTO.builder()
                .id(clientId.toString())
                .name(clientFeignClient.getClientById(clientId).getName())
                .build();
        List<AccountTO> accounts = new ArrayList<>();

        movementsByAccount.forEach((account, movements) -> {
            AccountTO accountTO = AccountTO.builder()
                    .type(account.getId().getType().toString())
                    .number(account.getId().getNumber())
                    .balance(account.getBalance())
                    .movements(movements.stream().map(
                            item -> MovementTO.builder()
                                    .date(item.getDate())
                                    .amount(item.getAmount())
                                    .movement(item.getMovement())
                                    .build()
                    ).collect(Collectors.toList()) )
                    .build();
            accounts.add(accountTO);

        });

        clientTO.setAccounts(accounts);
        return clientTO;

    }
}
