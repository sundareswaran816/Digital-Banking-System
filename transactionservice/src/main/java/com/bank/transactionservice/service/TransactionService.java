package com.bank.transactionservice.service;

import com.bank.transactionservice.dto.TransactionRequest;
import com.bank.transactionservice.dto.TransactionResponse;
import com.bank.transactionservice.dto.TransferRequest;

import java.util.List;

public interface TransactionService {

    TransactionResponse deposit(TransactionRequest request);

    TransactionResponse withdraw(TransactionRequest request);

    TransactionResponse transfer(TransferRequest request);

    TransactionResponse getTransactionById(Long transactionId);

    List<TransactionResponse> getAllTransactions();

    List<TransactionResponse> getTransactionsByAccountId(Long accountId);
}