package org.example.service.impl;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import java.math.BigDecimal;

/**
 * Implementation of the UserService interface.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of UserServiceImpl with the provided UserRepository.
     *
     * @param userRepository The repository responsible for data storage and retrieval.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return userRepository.checkPassword(user, password);
    }

    @Override
    public void viewBalance(User user) {
        userRepository.viewBalance(user);
    }

    @Override
    public BigDecimal checkCash(User user) {
        return userRepository.checkCash(user);
    }

    @Override
    public int getUserId(User user) {
        return userRepository.getUserId(user);
    }

    @Override
    public void deleteCash(Integer id, BigDecimal initialBalance) {
        userRepository.deleteCash(id, initialBalance);
    }

    @Override
    public void addCashUser(Integer userId, BigDecimal addBalanceUser) {
        userRepository.addCashUser(userId, addBalanceUser);
    }
}
