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
import service_account.service_account.utils.dto.AccountTO;
import service_account.service_account.utils.exceptions.CustomNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private AccountTO accountTO;
    private AccountId accountId;

    @BeforeEach
    void setUp() {
        accountId = new AccountId();
        accountId.setType(AccountType.SAVINGS);
        accountId.setNumber("123456");

        account = new Account();
        account.setId(accountId);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setState(true);
        account.setClientId(UUID.randomUUID());

        accountTO = AccountTO.builder()
                .type(account.getId().getType().toString())
                .number(account.getId().getNumber())
                .clientId(account.getClientId().toString())
                .balance(account.getBalance())
                .state(account.getState())
                .build();
    }

    @Test
    void testGetById_ShouldReturnAccountTO() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountTO result = accountService.getById(accountId.getType().toString(), accountId.getNumber());

        assertNotNull(result);
        assertEquals(account.getId().getNumber(), result.getNumber());
        assertEquals(account.getBalance(), result.getBalance());
    }

    @Test
    void testGetById_NotFound_ShouldThrowException() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(CustomNotFoundException.class, () -> accountService.getById(accountId.getType().toString(), accountId.getNumber()));
    }

    @Test
    void testGetAll_ShouldReturnAccountList() {
        when(accountRepository.findAll()).thenReturn(List.of(account));

        List<AccountTO> result = accountService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testCreateAccount_ShouldReturnCreatedAccountTO() {
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(account);

        AccountTO result = accountService.createAccount(accountTO);

        assertNotNull(result);
        assertEquals(accountTO.getNumber(), result.getNumber());
    }

    @Test
    void testUpdateAccount_ShouldReturnUpdatedAccountTO() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.saveAndFlush(any(Account.class))).thenReturn(account);

        AccountTO result = accountService.updateAccount(accountTO, accountId.getType().toString(), accountId.getNumber());

        assertNotNull(result);
        assertEquals(accountTO.getBalance(), result.getBalance());
    }

    @Test
    void testDeleteAccount_ShouldCallRepositoryDelete() {
        doNothing().when(accountRepository).deleteById(accountId);

        assertDoesNotThrow(() -> accountService.deleteAccount(accountId.getType().toString(), accountId.getNumber()));
        verify(accountRepository, times(1)).deleteById(accountId);
    }
}