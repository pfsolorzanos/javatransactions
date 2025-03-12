package service_account.service_account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service_account.service_account.model.Account;
import service_account.service_account.model.AccountId;
import service_account.service_account.repository.AccountRepository;
import service_account.service_account.utils.AccountType;
import service_account.service_account.utils.exceptions.CustomNotFoundException;
import service_account.service_account.utils.exceptions.CustomPreconditionFailedException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountBalanceServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountBalanceService accountBalanceService;

    private Account account;
    private AccountId accountId;

    @BeforeEach
    void setUp() {
        accountId = new AccountId();
        accountId.setType(AccountType.SAVINGS);
        accountId.setNumber("12345");

        account = new Account();
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(100));
    }

    @Test
    void testUpdateBalance_SuccessfulDeposit() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.saveAndFlush(any())).thenReturn(account);

        BigDecimal newBalance = accountBalanceService.updateBalance("SAVINGS", "12345", BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), newBalance);
    }

    @Test
    void testUpdateBalance_SuccessfulWithdrawal() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.saveAndFlush(any())).thenReturn(account);

        BigDecimal newBalance = accountBalanceService.updateBalance("SAVINGS", "12345", BigDecimal.valueOf(-50));
        assertEquals(BigDecimal.valueOf(50), newBalance);
    }

    @Test
    void testUpdateBalance_InsufficientFunds_ShouldThrowException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(CustomPreconditionFailedException.class,
                () -> accountBalanceService.updateBalance("SAVINGS", "12345", BigDecimal.valueOf(-200)));
    }

    @Test
    void testUpdateBalance_AccountNotFound_ShouldThrowException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class,
                () -> accountBalanceService.updateBalance("SAVINGS", "12345", BigDecimal.valueOf(50)));
    }
}