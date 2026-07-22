package com.bank.accountservice.service;

import com.bank.accountservice.dto.AccountRequest;
import com.bank.accountservice.dto.AccountResponse;
import com.bank.accountservice.dto.BalanceUpdateRequest;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.exception.InsufficientBalanceException;
import com.bank.accountservice.exception.ResourceNotFoundException;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse createAccount(AccountRequest request) {

        log.info("Creating account for customer {}", request.getCustomerId());

        Account account = Account.builder()
                .customerId(request.getCustomerId())
                .accountNumber(generateAccountNumber())
                .accountType(request.getAccountType())
                .balance(request.getBalance())
                .status("ACTIVE")
                .build();

        Account savedAccount = accountRepository.save(account);

        log.info("Account created successfully: {}", savedAccount.getAccountNumber());

        return mapToResponse(savedAccount);
    }

    @Override
    public AccountResponse getAccountById(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with ID " + accountId));

        return mapToResponse(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AccountResponse deposit(Long accountId, BalanceUpdateRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with ID " + accountId));

        account.setBalance(account.getBalance().add(request.getAmount()));

        return mapToResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse withdraw(Long accountId, BalanceUpdateRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with ID " + accountId));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        return mapToResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse freezeAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with ID " + accountId));

        account.setStatus("FROZEN");

        return mapToResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse activateAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with ID " + accountId));

        account.setStatus("ACTIVE");

        return mapToResponse(accountRepository.save(account));
    }

    @Override
    public void deleteAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with ID " + accountId));

        accountRepository.delete(account);

        log.info("Account deleted successfully");
    }

    private String generateAccountNumber() {
        return "ACC" + (100000 + new Random().nextInt(900000));
    }

    private AccountResponse mapToResponse(Account account) {

        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .customerId(account.getCustomerId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }
}