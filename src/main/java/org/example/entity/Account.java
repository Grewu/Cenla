package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Account {
    private String nameUser;
    private Integer userId;
    private String bankName;
    private Integer id;
    private int accountId;
    private Integer idUser;
    private BigDecimal balance;
    private Integer idBank;
    private String accountNumber;

    public Account(BigDecimal initialBalance, Integer bankNumber, String bankName, int userId, String accountNumber) {
        this.balance = initialBalance;
        this.idBank = bankNumber;
        this.bankName = bankName;
        this.idUser = userId;
        this.accountNumber = accountNumber;
    }

    public Account(int id, BigDecimal balance, int userId, int bankId, String bankName) {
        this.id = id;
        this.balance = balance;
        this.idBank = bankId;
        this.userId = userId;
        this.bankName = bankName;
    }

    public Account(int accountId, int userId, int bankId, BigDecimal balance, String accountNumber, String nameUser) {
        this.accountId = accountId;
        this.userId = userId;
        this.idBank = bankId;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.nameUser = nameUser;
    }

    public static String generateAccountNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").substring(0, 10);
    }
}
