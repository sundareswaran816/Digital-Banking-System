package com.bank.loanservice.controller;

import com.bank.loanservice.dto.LoanRequest;
import com.bank.loanservice.dto.LoanResponse;
import com.bank.loanservice.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse applyLoan(@Valid @RequestBody LoanRequest request) {
        return loanService.applyLoan(request);
    }

    @GetMapping("/{loanId}")
    public LoanResponse getLoanById(@PathVariable Long loanId) {
        return loanService.getLoanById(loanId);
    }

    @GetMapping
    public List<LoanResponse> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/customer/{customerId}")
    public List<LoanResponse> getLoansByCustomer(@PathVariable Long customerId) {
        return loanService.getLoansByCustomerId(customerId);
    }

    @PutMapping("/{loanId}/approve")
    public LoanResponse approveLoan(@PathVariable Long loanId) {
        return loanService.approveLoan(loanId);
    }

    @PutMapping("/{loanId}/reject")
    public LoanResponse rejectLoan(@PathVariable Long loanId) {
        return loanService.rejectLoan(loanId);
    }

    @DeleteMapping("/{loanId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
    }
}