package service_account.service_account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service_account.service_account.service.BankStatementService;
import service_account.service_account.utils.dto.bankStatement.ClientTO;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/reportes")
public class ReportController {

    @Autowired
    private BankStatementService bankStatementService;

    @GetMapping
    public ClientTO getById(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                            @RequestParam String clientId) {
        return bankStatementService.getStatementByDateRange(dateFrom, dateTo, UUID.fromString(clientId));

    }
}
