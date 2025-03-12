package service_account.service_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.UUID;

@Service
public class MovementService {
    private static final String INVALID_AMOUNT = "El monto no puede ser nulo";
    private static final String MOVEMENT_NOT_FOUND = "Movimiento no encontrado";
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountBalanceService accountBalanceService;


    private Movement getMovementById(UUID id) {
        return movementRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(MOVEMENT_NOT_FOUND));
    }

    public MovementTO getById(UUID id) {
        Movement movement = getMovementById(id);
        return MovementTO.builder()
                .id(movement.getId().toString())
                .type(movement.getAccount().getId().getType().toString())
                .number(movement.getAccount().getId().getNumber())
                .movement(movement.getMovement())
                .balance(movement.getBalance())
                .date(movement.getDate())
                .build();
    }

    public List<MovementTO> getAll() {
        return movementRepository.findAll().stream().map(item -> MovementTO.builder()
                .type(item.getAccount().getId().getType().toString())
                .number(item.getAccount().getId().getNumber())
                .date(item.getDate())
                .amount(item.getAmount())
                .balance(item.getBalance())
                .id(item.getId().toString())
                .movement(item.getMovement())
                .build()).toList();

    }

    @Transactional
    public MovementTO createMovement(MovementTO movementTO) {

        BigDecimal amount = movementTO.getAmount();
        if (amount == null) {
            throw new CustomConflictException(INVALID_AMOUNT);
        }
        AccountId accountId = new AccountId();
        String type = movementTO.getType();
        String number = movementTO.getNumber();
        accountId.setType(AccountType.valueOf(type));
        accountId.setNumber(number);
        Account account = accountService.getAccountById(accountId);
        Movement movement = new Movement();
        movement.setAccount(account);
        movement.setMovement(movementTO.getMovement());
        movement.setDate(movementTO.getDate());
        movement.setAmount(amount);
        BigDecimal newAccountBalance = accountBalanceService.updateBalance(type, number, amount);
        movement.setBalance(newAccountBalance);
        movementRepository.saveAndFlush(movement);
        return movementTO;
    }

    @Transactional
    public MovementTO updateMovement(MovementTO movementTO, UUID id) {
        Movement movement = getMovementById(id);
        AccountId accountId = new AccountId();
        accountId.setType(AccountType.valueOf(movementTO.getType()));
        accountId.setNumber(movementTO.getNumber());
        Account account = accountService.getAccountById(accountId);
        movement.setAccount(account);
        movement.setMovement(movementTO.getMovement());
        movement.setDate(movementTO.getDate());
        movement.setBalance(movementTO.getBalance());
        movement.setAmount(movementTO.getAmount());
        movementRepository.saveAndFlush(movement);
        return movementTO;
    }

    @Transactional
    public void deleteMovement(UUID id) {
        movementRepository.deleteById(id);
    }
}
