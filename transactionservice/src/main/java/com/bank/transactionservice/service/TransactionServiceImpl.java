package com.bank.transactionservice.service;

import com.bank.transactionservice.client.AccountClient;
import com.bank.transactionservice.client.BalanceUpdateRequest;
import com.bank.transactionservice.dto.TransactionRequest;
import com.bank.transactionservice.dto.TransactionResponse;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.entity.Transaction;
import com.bank.transactionservice.exception.ResourceNotFoundException;
import com.bank.transactionservice.repository.TransactionRepository;
import com.bank.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    @Override
    public TransactionResponse deposit(TransactionRequest request) {

        log.info("Depositing {} to account {}",
                request.getAmount(), request.getAccountId());

        accountClient.deposit(
                request.getAccountId(),
                new BalanceUpdateRequest(request.getAmount())
        );

        Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .transactionType("DEPOSIT")
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status("SUCCESS")
                .remarks(request.getRemarks())
                .build();

        return mapToResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse withdraw(TransactionRequest request) {

        log.info("Withdrawing {} from account {}",
                request.getAmount(), request.getAccountId());

        accountClient.withdraw(
                request.getAccountId(),
                new BalanceUpdateRequest(request.getAmount())
        );

        Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .transactionType("WITHDRAW")
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status("SUCCESS")
                .remarks(request.getRemarks())
                .build();

        return mapToResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse transfer(TransferRequest request) {

        log.info("Transferring {} from {} to {}",
                request.getAmount(),
                request.getSourceAccountId(),
                request.getDestinationAccountId());

        accountClient.withdraw(
                request.getSourceAccountId(),
                new BalanceUpdateRequest(request.getAmount())
        );

        accountClient.deposit(
                request.getDestinationAccountId(),
                new BalanceUpdateRequest(request.getAmount())
        );

        Transaction transaction = Transaction.builder()
                .accountId(request.getSourceAccountId())
                .destinationAccountId(request.getDestinationAccountId())
                .transactionType("TRANSFER")
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status("SUCCESS")
                .remarks(request.getRemarks())
                .build();

        return mapToResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse getTransactionById(Long transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found with ID " + transactionId));

        return mapToResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TransactionResponse> getTransactionsByAccountId(Long accountId) {

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(transaction.getAccountId())
                .destinationAccountId(transaction.getDestinationAccountId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .remarks(transaction.getRemarks())
                .build();
    }
}