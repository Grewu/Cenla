package org.example.service.impl;

import org.example.entity.Account;
import org.example.entity.Transaction;
import org.example.repository.TransactionRepository;
import org.example.service.TransactionService;
import org.example.util.config.ConfigReader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the TransactionService interface.
 */
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;


    /**
     * Constructs a TransactionServiceImpl with the provided TransactionRepository.
     *
     * @param transactionRepository The TransactionRepository to be used.
     */
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void getTransferToOtherBank(String senderAccountId, BigDecimal transferAmount, String receivingAccountNumber) {
        transactionRepository.getTransferToOtherBank(senderAccountId, transferAmount, receivingAccountNumber);
    }

    @Override
    public String getNameOfBank(String senderBankAccount) {
        return transactionRepository.getNameOfBank(senderBankAccount);
    }

    @Override
    public void startInterestCalculation() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                BigDecimal interestRate = ConfigReader.getConfiguredInterestRate();

                List<Account> accounts = transactionRepository.getAllAccounts();
                for (Account account : accounts) {
                    BigDecimal currentBalance = account.getBalance();
                    BigDecimal interestAmount = currentBalance.multiply(interestRate);
                    transactionRepository.deposit(account.getAccountId(), interestAmount);
                    Transaction transaction = new Transaction(account.getAccountId(), "sender", "receiver", interestAmount, "transfer", LocalDateTime.now());
                    transactionRepository.save(transaction);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}
