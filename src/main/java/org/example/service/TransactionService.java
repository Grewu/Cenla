package org.example.service;

import org.example.entity.Account;
import org.example.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for transaction-related operations.
 */
public interface TransactionService {

    /**
     * Performs a transfer from one bank to another.
     *
     * @param senderAccountId      The sender's account ID.
     * @param transferAmount       The amount to transfer.
     * @param receivingAccountNumber The receiving account's number.
     */
    void getTransferToOtherBank(String senderAccountId, BigDecimal transferAmount, String receivingAccountNumber);

    /**
     * Retrieves the name of the bank associated with a sender's account.
     *
     * @param senderBankAccount The sender's bank account number.
     * @return The name of the bank.
     */
    String getNameOfBank(String senderBankAccount);


    void startInterestCalculation();
}
