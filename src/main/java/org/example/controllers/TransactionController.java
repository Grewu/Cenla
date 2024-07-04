package org.example.controllers;

import org.example.repository.TransactionRepository;
import org.example.repository.impl.TransactionRepositoryImpl;
import org.example.service.TransactionService;
import org.example.service.impl.TransactionServiceImpl;

import java.math.BigDecimal;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Controller class responsible for handling transaction-related operations.
 */
public final class TransactionController {
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private final TransactionService transactionService = new TransactionServiceImpl(transactionRepository);

    private ScheduledExecutorService scheduler;


    /**
     * Performs a transfer from one bank to another.
     *
     * @param senderAccountNumber    The sender's account number.
     * @param transferAmount         The amount to transfer.
     * @param receivingAccountNumber The receiving account's number.
     */
    public void getTransferToOtherBank(String senderAccountNumber, BigDecimal transferAmount, String receivingAccountNumber) {
        transactionService.getTransferToOtherBank(senderAccountNumber, transferAmount, receivingAccountNumber);
    }

    /**
     * Starts the periodic calculation of interest for accounts.
     * Runs every minute to calculate interest and deposit it into eligible accounts.
     */
    public void startInterestCalculation() {
        transactionService.startInterestCalculation();
    }

    /**
     * Retrieves the name of the bank associated with a sender's account.
     *
     * @param senderBankAccount The sender's bank account number.
     * @return The name of the bank.
     */
    public String getNameOfBank(String senderBankAccount) {
        return transactionService.getNameOfBank(senderBankAccount);
    }

    /**
     * Stop the periodic calculation of interest for accounts.
     */
    public void stopInterestCalculation() {
        scheduler.shutdown();
    }
}
