package com.bank.transactionservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long transactionId;
    private Long accountId;
    private Long destinationAccountId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String status;
    private String remarks;
}