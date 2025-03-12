package service_account.service_account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import service_account.service_account.model.Account;
import service_account.service_account.model.AccountId;
import service_account.service_account.model.Movement;
import service_account.service_account.repository.MovementRepository;
import service_account.service_account.utils.AccountType;
import service_account.service_account.utils.dto.MovementTO;
import service_account.service_account.utils.exceptions.CustomConflictException;
import service_account.service_account.utils.exceptions.CustomNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovementServiceTest {
    private static final String ACCOUNT_NUMBER = "22233344400";
    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountBalanceService accountBalanceService;

    @InjectMocks
    private MovementService movementService;

    private UUID movementId;
    private Movement movement;
    private MovementTO movementTO;
    private Account account;

    @BeforeEach
    void setUp() {
        AccountId accountId = new AccountId();
        accountId.setType(AccountType.SAVINGS);
        accountId.setNumber(ACCOUNT_NUMBER);
        movementId = UUID.randomUUID();
        account = new Account();
        account.setId(accountId);
        movement = new Movement();
        movement.setId(movementId);
        movement.setAccount(account);
        movement.setAmount(BigDecimal.TEN);
        movement.setBalance(BigDecimal.valueOf(100));

        movementTO = MovementTO.builder()
                .id(movementId.toString())
                .amount(BigDecimal.TEN)
                .balance(BigDecimal.valueOf(100))
                .type(AccountType.SAVINGS.toString())
                .number(ACCOUNT_NUMBER)
                .build();
    }

    @Test
    void testGetById_WhenMovementExists() {
        when(movementRepository.findById(movementId)).thenReturn(Optional.of(movement));
        MovementTO result = movementService.getById(movementId);
        assertNotNull(result);
        assertEquals(movementId.toString(), result.getId());
    }

    @Test
    void testGetById_WhenMovementDoesNotExist_ShouldThrowException() {
        when(movementRepository.findById(movementId)).thenReturn(Optional.empty());
        assertThrows(CustomNotFoundException.class, () -> movementService.getById(movementId));
    }

    @Test
    void testGetAll() {
        when(movementRepository.findAll()).thenReturn(List.of(movement));
        List<MovementTO> result = movementService.getAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @Transactional
    void testCreateMovement_ShouldSaveAndReturnMovementTO() {
        when(accountService.getAccountById(any())).thenReturn(account);
        when(accountBalanceService.updateBalance(anyString(), anyString(), any())).thenReturn(BigDecimal.valueOf(110));
        when(movementRepository.saveAndFlush(any())).thenReturn(movement);

        MovementTO result = movementService.createMovement(movementTO);
        assertNotNull(result);
        assertEquals(BigDecimal.TEN, result.getAmount());
    }

    @Test
    void testCreateMovement_WithNullAmount_ShouldThrowException() {
        movementTO.setAmount(null);
        assertThrows(CustomConflictException.class, () -> movementService.createMovement(movementTO));
    }

    @Test
    @Transactional
    void testUpdateMovement_ShouldUpdateAndReturnMovementTO() {
        when(movementRepository.findById(movementId)).thenReturn(Optional.of(movement));
        when(accountService.getAccountById(any())).thenReturn(account);
        when(movementRepository.saveAndFlush(any())).thenReturn(movement);

        MovementTO result = movementService.updateMovement(movementTO, movementId);
        assertNotNull(result);
    }

    @Test
    @Transactional
    void testDeleteMovement() {
        doNothing().when(movementRepository).deleteById(movementId);
        assertDoesNotThrow(() -> movementService.deleteMovement(movementId));
        verify(movementRepository, times(1)).deleteById(movementId);
    }
}