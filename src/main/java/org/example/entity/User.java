package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private BigDecimal cash;

    public User(String username, String password, String email, BigDecimal cash) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.cash = cash;
    }
}
