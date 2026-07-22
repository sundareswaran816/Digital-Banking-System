package com.bank.loanservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponse {

    private Long loanId;

    private Long customerId;

    private Long accountId;

    private BigDecimal loanAmount;

    private Double interestRate;

    private Integer tenureInMonths;

    private LocalDate applicationDate;

    private String loanType;

    private String status;
}