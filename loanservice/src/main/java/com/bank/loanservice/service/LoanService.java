package com.bank.loanservice.service;

import com.bank.loanservice.dto.LoanRequest;
import com.bank.loanservice.dto.LoanResponse;

import java.util.List;

public interface LoanService {

    LoanResponse applyLoan(LoanRequest request);

    LoanResponse getLoanById(Long loanId);

    List<LoanResponse> getAllLoans();

    List<LoanResponse> getLoansByCustomerId(Long customerId);

    LoanResponse approveLoan(Long loanId);

    LoanResponse rejectLoan(Long loanId);

    void deleteLoan(Long loanId);
}