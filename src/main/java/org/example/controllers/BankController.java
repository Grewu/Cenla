package org.example.controllers;

import org.example.repository.BankRepository;
import org.example.repository.impl.BankRepositoryImpl;
import org.example.service.BankService;
import org.example.service.impl.BankServiceImpl;

public final class BankController {
    private final BankRepository bankRepository = new BankRepositoryImpl();
    private final BankService bankService = new BankServiceImpl(bankRepository);

    /**
     * Displays the IDs of all banks.
     */
    public void viewBanksID() {
        bankService.viewBanksID();
    }

    /**
     * Retrieves information about a bank by its ID.
     *
     * @param bankNumber The ID of the bank.
     * @return Information about the bank.
     */
    public String getBankById(Integer bankNumber) {
        return bankService.getBankById(bankNumber);
    }
}