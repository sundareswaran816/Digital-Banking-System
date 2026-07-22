package com.bank.transactionservice.client;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Long accountId;
    private Long customerId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String status;
}