package service_account.service_account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service_account.service_account.model.Account;
import service_account.service_account.model.AccountId;
import service_account.service_account.repository.AccountRepository;
import service_account.service_account.utils.AccountType;
import service_account.service_account.utils.exceptions.CustomNotFoundException;
import service_account.service_account.utils.exceptions.CustomPreconditionFailedException;

import java.math.BigDecimal;

@Service
public class AccountBalanceService {
    private static final String INSUFFICIENT_FUNDS = "Saldo no disponible";
    private static final String ACCOUNT_NOT_FOUND = "Cuenta no encontrada";
    @Autowired
    private AccountRepository accountRepository;

    private Account getAccountById(String type, String number) {
        AccountId accountId = new AccountId();
        accountId.setType(AccountType.valueOf(type));
        accountId.setNumber(number);
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomNotFoundException(ACCOUNT_NOT_FOUND));
    }
    @Transactional
    public BigDecimal updateBalance(String type, String number, BigDecimal amount) {
        Account account = getAccountById(type, number);
        BigDecimal accountBalance = account.getBalance();
        BigDecimal newAccountBalance = accountBalance.add(amount);
        if (newAccountBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomPreconditionFailedException(INSUFFICIENT_FUNDS);
        }
        account.setBalance(newAccountBalance);
        accountRepository.saveAndFlush(account);
        return newAccountBalance;
    }
}
