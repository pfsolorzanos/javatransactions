package service_account.service_account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service_account.service_account.service.AccountService;
import service_account.service_account.utils.dto.AccountTO;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{type}/{number}")
    public AccountTO getById(@PathVariable String type, @PathVariable String number) {
        return accountService.getById(type, number);
    }
    @GetMapping
    public List<AccountTO> getAll() {
        return accountService.getAll();
    }

    @PostMapping
    public AccountTO create(@RequestBody AccountTO accountTO) {
        return accountService.createAccount(accountTO);
    }
    @PutMapping("/{type}/{number}")
    public AccountTO update(@PathVariable String type, @PathVariable String number, @RequestBody AccountTO accountTO) {
        return accountService.updateAccount(accountTO, type, number);
    }

    @DeleteMapping("/{type}/{number}")
    public void delete(@PathVariable String type, @PathVariable String number) {
        accountService.deleteAccount(type, number);
    }
}
