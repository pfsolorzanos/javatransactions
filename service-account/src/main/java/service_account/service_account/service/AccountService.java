package service_account.service_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service_account.service_account.model.Account;
import service_account.service_account.model.AccountId;
import service_account.service_account.repository.AccountRepository;
import service_account.service_account.utils.AccountType;
import service_account.service_account.utils.dto.AccountTO;
import service_account.service_account.utils.exceptions.CustomNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private static final String ACCOUNT_NOT_FOUND = "Cuenta no encontrada";
    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(AccountId id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ACCOUNT_NOT_FOUND));
    }

    public AccountTO getById(String type, String number) {
        AccountId accountId = new AccountId();
        accountId.setType(AccountType.valueOf(type));
        accountId.setNumber(number);
        Account account = getAccountById(accountId);
        return AccountTO.builder()
                .type(account.getId().getType().toString())
                .number(account.getId().getNumber())
                .clientId(account.getClientId().toString())
                .balance(account.getBalance())
                .state(account.getState())
                .build();

    }

    public List<AccountTO> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(item -> AccountTO.builder()
                .type(item.getId().getType().toString())
                .number(item.getId().getNumber())
                .clientId(item.getClientId().toString())
                .balance(item.getBalance())
                .state(item.getState())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    public AccountTO createAccount(AccountTO accountTO) {
        AccountId accountId = new AccountId();
        AccountType accountType = AccountType.valueOf(accountTO.getType());
        accountId.setType(accountType);
        accountId.setNumber(accountTO.getNumber());
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(accountTO.getBalance());
        account.setState(accountTO.getState());
        account.setClientId(UUID.fromString(accountTO.getClientId()));
        accountRepository.saveAndFlush(account);
        return accountTO;
    }

    @Transactional
    public AccountTO updateAccount(AccountTO accountTO, String type, String number) {
        AccountId accountId = new AccountId();
        accountId.setType(AccountType.valueOf(type));
        accountId.setNumber(number);
        Account account = getAccountById(accountId);
        account.setClientId(UUID.fromString(accountTO.getClientId()));
        account.setState(accountTO.getState());
        account.setBalance(accountTO.getBalance());
        accountRepository.saveAndFlush(account);
        return accountTO;
    }

    @Transactional
    public void deleteAccount(String type, String number) {
        AccountId accountId = new AccountId();
        accountId.setType(AccountType.valueOf(type));
        accountId.setNumber(number);
        accountRepository.deleteById(accountId);
    }

}
