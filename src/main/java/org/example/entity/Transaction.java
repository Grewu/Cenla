package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer id;

    private String senderAccountNumber;

    private String receiverAccountNumber;

    private String time;

    private BigDecimal amount;

    private String transactionType;

    private LocalDateTime timestamp;

    public Transaction(String time, String type, BigDecimal amount) {
        this.time = time;
        this.transactionType = type;
        this.amount = amount;
    }

    public Transaction(Integer accountId, String sender, String receiver, BigDecimal interestAmount, String transfer, LocalDateTime time) {
        this.id = accountId;
        this.senderAccountNumber = sender;
        this.receiverAccountNumber = receiver;
        this.amount = interestAmount;
        this.transactionType = transfer;
        this.timestamp = time;
    }
}
