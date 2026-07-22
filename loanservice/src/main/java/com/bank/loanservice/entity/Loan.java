package com.bank.loanservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Integer tenureInMonths;

    @Column(nullable = false)
    private LocalDate applicationDate;

    @Column(nullable = false)
    private String loanType;

    @Column(nullable = false)
    private String status;
}