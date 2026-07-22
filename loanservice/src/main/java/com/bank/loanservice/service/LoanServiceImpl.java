package com.bank.loanservice.service;

import com.bank.loanservice.dto.LoanRequest;
import com.bank.loanservice.dto.LoanResponse;
import com.bank.loanservice.entity.Loan;
import com.bank.loanservice.exception.ResourceNotFoundException;
import com.bank.loanservice.repository.LoanRepository;
import com.bank.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public LoanResponse applyLoan(LoanRequest request) {

        log.info("Loan application received from Customer ID: {}", request.getCustomerId());

        Loan loan = Loan.builder()
                .customerId(request.getCustomerId())
                .accountId(request.getAccountId())
                .loanAmount(request.getLoanAmount())
                .interestRate(request.getInterestRate())
                .tenureInMonths(request.getTenureInMonths())
                .applicationDate(LocalDate.now())
                .loanType(request.getLoanType())
                .status("PENDING")
                .build();

        loan = loanRepository.save(loan);

        return mapToResponse(loan);
    }

    @Override
    public LoanResponse getLoanById(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found with ID: " + loanId));

        return mapToResponse(loan);
    }

    @Override
    public List<LoanResponse> getAllLoans() {

        return loanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<LoanResponse> getLoansByCustomerId(Long customerId) {

        return loanRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LoanResponse approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found with ID: " + loanId));

        loan.setStatus("APPROVED");

        loan = loanRepository.save(loan);

        log.info("Loan Approved: {}", loanId);

        return mapToResponse(loan);
    }

    @Override
    public LoanResponse rejectLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found with ID: " + loanId));

        loan.setStatus("REJECTED");

        loan = loanRepository.save(loan);

        log.info("Loan Rejected: {}", loanId);

        return mapToResponse(loan);
    }

    @Override
    public void deleteLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found with ID: " + loanId));

        loanRepository.delete(loan);

        log.info("Loan Deleted: {}", loanId);
    }

    private LoanResponse mapToResponse(Loan loan) {

        return LoanResponse.builder()
                .loanId(loan.getLoanId())
                .customerId(loan.getCustomerId())
                .accountId(loan.getAccountId())
                .loanAmount(loan.getLoanAmount())
                .interestRate(loan.getInterestRate())
                .tenureInMonths(loan.getTenureInMonths())
                .applicationDate(loan.getApplicationDate())
                .loanType(loan.getLoanType())
                .status(loan.getStatus())
                .build();
    }
}