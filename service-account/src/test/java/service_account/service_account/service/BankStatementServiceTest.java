package service_account.service_account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service_account.service_account.feign.ClientFeignClient;
import service_account.service_account.model.Account;
import service_account.service_account.model.AccountId;
import service_account.service_account.model.Movement;
import service_account.service_account.repository.MovementRepository;
import service_account.service_account.utils.AccountType;
import service_account.service_account.utils.dto.feign.ClientFeignTO;
import service_account.service_account.utils.dto.bankStatement.ClientTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankStatementServiceTest {
    @Mock
    private ClientFeignClient clientFeignClient;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private BankStatementService bankStatementService;

    private UUID clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private ClientFeignTO clientFeignTO;
    private Account account;
    private Movement movement;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        dateFrom = LocalDate.now().minusDays(10);
        dateTo = LocalDate.now();

        clientFeignTO = ClientFeignTO.builder()
                .id(clientId.toString())
                .name("John Doe")
                .build();

        AccountId accountId = new AccountId();
        accountId.setType(AccountType.SAVINGS);
        accountId.setNumber("123456");

        account = new Account();
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(1000));

        movement = new Movement();
        movement.setAccount(account);
        movement.setDate(LocalDate.now());
        movement.setAmount(BigDecimal.valueOf(200));
        movement.setMovement("Deposit");
    }

    @Test
    void testGetStatementByDateRange_ShouldReturnClientTO() {
        when(movementRepository.findMovementsByClientIdAndDateRange(dateFrom, dateTo, clientId))
                .thenReturn(List.of(movement));
        when(clientFeignClient.getClientById(clientId)).thenReturn(clientFeignTO);

        ClientTO result = bankStatementService.getStatementByDateRange(dateFrom, dateTo, clientId);

        assertNotNull(result);
        assertEquals(clientId.toString(), result.getId());
        assertEquals("John Doe", result.getName());
        assertFalse(result.getAccounts().isEmpty());
        assertEquals(1, result.getAccounts().size());
    }

    @Test
    void testGetStatementByDateRange_NoMovements_ShouldReturnEmptyAccounts() {
        when(movementRepository.findMovementsByClientIdAndDateRange(dateFrom, dateTo, clientId))
                .thenReturn(Collections.emptyList());
        when(clientFeignClient.getClientById(clientId)).thenReturn(clientFeignTO);

        ClientTO result = bankStatementService.getStatementByDateRange(dateFrom, dateTo, clientId);

        assertNotNull(result);
        assertTrue(result.getAccounts().isEmpty());
    }
}