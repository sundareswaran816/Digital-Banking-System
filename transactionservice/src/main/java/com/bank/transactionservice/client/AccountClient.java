package com.bank.transactionservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final RestTemplate restTemplate;

    private static final String ACCOUNT_SERVICE_URL = "http://localhost:8082/accounts";

    public AccountResponse deposit(Long accountId, BalanceUpdateRequest request) {

        restTemplate.put(
                ACCOUNT_SERVICE_URL + "/" + accountId + "/deposit",
                request
        );

        return restTemplate.getForObject(
                ACCOUNT_SERVICE_URL + "/" + accountId,
                AccountResponse.class
        );
    }

    public AccountResponse withdraw(Long accountId, BalanceUpdateRequest request) {

        restTemplate.put(
                ACCOUNT_SERVICE_URL + "/" + accountId + "/withdraw",
                request
        );

        return restTemplate.getForObject(
                ACCOUNT_SERVICE_URL + "/" + accountId,
                AccountResponse.class
        );
    }

    public AccountResponse getAccount(Long accountId) {

        return restTemplate.getForObject(
                ACCOUNT_SERVICE_URL + "/" + accountId,
                AccountResponse.class
        );
    }
}