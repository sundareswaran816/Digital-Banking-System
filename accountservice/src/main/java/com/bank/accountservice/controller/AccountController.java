package com.bank.accountservice.controller;

import com.bank.accountservice.dto.AccountRequest;
import com.bank.accountservice.dto.AccountResponse;
import com.bank.accountservice.dto.BalanceUpdateRequest;
import com.bank.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request) {

        log.info("Received request to create account");

        return accountService.createAccount(request);
    }

    @GetMapping("/{accountId}")
    public AccountResponse getAccountById(@PathVariable Long accountId) {

        log.info("Received request to fetch account {}", accountId);

        return accountService.getAccountById(accountId);
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {

        log.info("Received request to fetch all accounts");

        return accountService.getAllAccounts();
    }

    @PutMapping("/{accountId}/deposit")
    public AccountResponse deposit(
            @PathVariable Long accountId,
            @Valid @RequestBody BalanceUpdateRequest request) {

        log.info("Received deposit request for account {}", accountId);

        return accountService.deposit(accountId, request);
    }

    @PutMapping("/{accountId}/withdraw")
    public AccountResponse withdraw(
            @PathVariable Long accountId,
            @Valid @RequestBody BalanceUpdateRequest request) {

        log.info("Received withdrawal request for account {}", accountId);

        return accountService.withdraw(accountId, request);
    }

    @PutMapping("/{accountId}/freeze")
    public AccountResponse freezeAccount(@PathVariable Long accountId) {

        log.info("Received freeze request for account {}", accountId);

        return accountService.freezeAccount(accountId);
    }

    @PutMapping("/{accountId}/activate")
    public AccountResponse activateAccount(@PathVariable Long accountId) {

        log.info("Received activate request for account {}", accountId);

        return accountService.activateAccount(accountId);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long accountId) {

        log.info("Received delete request for account {}", accountId);

        accountService.deleteAccount(accountId);
    }
}