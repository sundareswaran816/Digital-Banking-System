package com.bank.accountservice.service;

import com.bank.accountservice.dto.AccountRequest;
import com.bank.accountservice.dto.AccountResponse;
import com.bank.accountservice.dto.BalanceUpdateRequest;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccountById(Long accountId);

    List<AccountResponse> getAllAccounts();

    AccountResponse deposit(Long accountId, BalanceUpdateRequest request);

    AccountResponse withdraw(Long accountId, BalanceUpdateRequest request);

    AccountResponse freezeAccount(Long accountId);

    AccountResponse activateAccount(Long accountId);

    void deleteAccount(Long accountId);
}