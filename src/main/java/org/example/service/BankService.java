package org.example.service;

/**
 * Service interface for bank-related operations.
 */
public interface BankService {

    /**
     * Retrieves and displays the IDs of all banks.
     */
    void viewBanksID();

    /**
     * Retrieves the name of the bank with the specified ID.
     *
     * @param bankNumber The ID of the bank.
     * @return The name of the bank.
     */
    String getBankById(Integer bankNumber);
}
