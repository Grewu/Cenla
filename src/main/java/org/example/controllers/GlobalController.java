package org.example.controllers;

import org.example.entity.Account;
import org.example.entity.User;
import org.example.util.chek.CheckGenerated;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public final class GlobalController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserController userController = new UserController();
    private static final AccountController accountController = new AccountController();
    private static final TransactionController transactionController = new TransactionController();
    private static final BankController bankController = new BankController();

    static {
        transactionController.startInterestCalculation();
    }

    public static void startConsoleApp() {
        while (true) {
            System.out.println("==Console Bank==");
            System.out.println("""
                    1 - Registration
                    2 - Login
                    0 - Exit
                    """);
            System.out.println("Choose number: ");
            String step = scanner.nextLine();

            switch (step) {
                case "1" -> registerUser();
                case "2" -> loginUser();
                case "0" -> {
                    System.out.println("Exiting the application.");
                    transactionController.stopInterestCalculation();
                    return;
                }
                default -> System.out.println("Incorrect input. Please try again.");
            }
        }
    }

    private static void userMenu(User user) {
        while (true) {
            printUserMenu();
            if (scanner.hasNext()) {
                String step = scanner.nextLine();
                switch (step) {
                    case "1" -> viewBalance(user);
                    case "2" -> viewBanksID();
                    case "3" -> openAccount(user);
                    case "4" -> viewUserAccounts(user);
                    case "5" -> deposit(user);
                    case "6" -> withdraw(user);
                    case "7" -> addBalanceUser(user);
                    case "8" -> transferToOtherBank();
                    case "9" -> closeAccount(user);
                    case "0" -> {
                        System.out.println("Exiting user menu.");
                        return;
                    }
                }
            } else {
                System.out.println("Input complete. Exiting user menu.");
                return;
            }
        }
    }


    private static void viewBanksID() {
        bankController.viewBanksID();
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void viewBalance(User user) {
        userController.viewBalance(user);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void openAccount(User user) {
        BigDecimal userBalance = userController.checkCash(user);

        System.out.println("Select the bank number where you want to open an account:");
        bankController.viewBanksID();
        Integer selectedBankNumber = nextValidInt();

        System.out.println("Your current balance: " + userBalance);
        System.out.println("Enter the initial balance for the new account:");
        BigDecimal initialBalance = scanner.nextBigDecimal();

        if (isInvalidInitialBalance(initialBalance, userBalance)) {
            System.out.println("The initial balance must be greater than zero and not exceed the current balance.");
        } else {
            createAccount(user, selectedBankNumber, initialBalance);
            System.out.println("Account successfully opened!");
        }
    }

    private static void createAccount(User user, Integer bankNumber, BigDecimal initialBalance) {
        String accountNumber = Account.generateAccountNumber();
        int userId = userController.getUserId(user);
        String bankName = bankController.getBankById(bankNumber);

        userController.deleteCash(userId, initialBalance);

        Account account = new Account(initialBalance, bankNumber, bankName, userId, accountNumber);
        accountController.creat(account);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }


    private static boolean isInvalidInitialBalance(BigDecimal initialBalance, BigDecimal userBalance) {
        return initialBalance.compareTo(BigDecimal.ZERO) <= 0 || initialBalance.compareTo(userBalance) > 0;
    }

    private static void viewUserAccounts(User user) {
        int id = userController.getUserId(user);
        List<Account> userAccounts = accountController.getAccountsByUserID(id);
        System.out.println("User " + user.getUsername() + "'s accounts:");
        for (Account account : userAccounts) {
            System.out.println("Account #" + account.getId());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("--------------------------");
        }
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void deposit(User user) {
        int numberOfAccount;
        BigDecimal userBalance = userController.checkCash(user);
        BigDecimal depositAmount;

        viewUserAccounts(user);

        while (true) {
            System.out.println("Enter the account number to deposit to:");
            try {
                numberOfAccount = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Enter the account number as an integer.");
                scanner.next();
            }
        }

        System.out.println("Your current balance: " + userBalance);

        do {
            System.out.println("Enter a positive amount to deposit:");
            depositAmount = scanner.nextBigDecimal();
        } while (depositAmount.compareTo(BigDecimal.ZERO) < 0);

        int userId = userController.getUserId(user);

        String accountNumber = accountController.getAccountByNumberOfAccount(numberOfAccount);

        checkGeneration("Deposit", accountController.getAccountByNumberOfAccount(numberOfAccount), depositAmount,
                accountNumber);

        accountController.updateBalanceUser(numberOfAccount, depositAmount, userId, accountNumber);
        System.out.println("Amount " + depositAmount + " successfully deposited to the account.");
    }

    private static void withdraw(User user) {
        int numberOfAccount;
        BigDecimal withdrawAmount;
        viewUserAccounts(user);

        while (true) {
            System.out.println("Enter the account number:");
            try {
                numberOfAccount = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Enter the account number as an integer.");
                scanner.next();
            }
        }

        do {
            System.out.println("Enter a positive amount to withdraw:");
            withdrawAmount = scanner.nextBigDecimal();
        } while (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0);

        String numberAccount = accountController.getAccountByNumberOfAccount(numberOfAccount);

        checkGeneration("Withdrawal", numberAccount, withdrawAmount,
                numberAccount);

        accountController.updateBalanceAccount(numberOfAccount, withdrawAmount, numberAccount);
        System.out.println("Amount " + withdrawAmount + " successfully withdrawn from the account.");
    }

    private static void addBalanceUser(User user) {
        Integer userId = userController.getUserId(user);
        BigDecimal addBalanceUser;

        System.out.println("Current balance in the account: ");
        userController.viewBalance(user);
        while (true) {
            System.out.println("Choose a positive top-up amount:");
            addBalanceUser = scanner.nextBigDecimal();
            if (addBalanceUser.compareTo(BigDecimal.ZERO) > 0) {
                break;
            }
            System.out.println("Incorrect input. Enter a positive top-up amount.");
        }
        userController.addCashUser(userId, addBalanceUser);
    }

    private static void transferToOtherBank() {
        BigDecimal transferAmount;
        System.out.println("Enter the sender account number:");
        String senderAccountId = scanner.next();

        do {
            System.out.println("Enter a positive transfer amount:");
            transferAmount = scanner.nextBigDecimal();
        } while (transferAmount.compareTo(BigDecimal.ZERO) <= 0);

        System.out.println("Enter the recipient account number:");
        String receivingAccountNumber = scanner.next();
        checkGeneration("Transfer", senderAccountId, transferAmount, receivingAccountNumber);

        transactionController.getTransferToOtherBank(senderAccountId, transferAmount, receivingAccountNumber);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void closeAccount(User user) {
        int numberOfAccount;
        viewUserAccounts(user);

        System.out.println("Select the account you want to close:");
        while (true) {
            try {
                numberOfAccount = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Enter the account number as an integer.");
                scanner.next();
            }
        }
        accountController.closeAccount(numberOfAccount);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    private static void registerUser() {
        System.out.println("=====Registration=====");
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Print your cash");
        BigDecimal cash = BigDecimal.valueOf(scanner.nextInt());
        scanner.nextLine();
        User user = new User(username, password, email, cash);
        userController.create(user);
    }

    private static void loginUser() {
        System.out.println("=====Login=====");
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        User user = userController.getUserByUsername(username);

        if (user == null) {
            System.out.println("User with such username not found.");
            return;
        }
        if (userController.checkPassword(user, password)) {
            System.out.println("Welcome, " + user.getUsername() + "!");
            userMenu(user);
        } else {
            System.out.println("Invalid password. Please try again.");
        }
    }

    private static void printUserMenu() {
        System.out.println("===User Menu===");
        System.out.println("1. View user balance");
        System.out.println("2. View banks");
        System.out.println("3. Open an account in a bank");
        System.out.println("4. View bank account");
        System.out.println("5. Deposit funds");
        System.out.println("6. Withdraw funds from the account");
        System.out.println("7. Add balance to user");
        System.out.println("8. Transfer to another bank account");
        System.out.println("9. Close bank account");
        System.out.println("10. Get a statement");
        System.out.println("0. Exit");
        System.out.println("Choose number: ");
    }

    private static void checkGeneration(String typeOfOperation, String senderAccountId, BigDecimal transferAmount,
                                        String receivingAccountNumber) {
        CheckGenerated.generateCheck(typeOfOperation, senderAccountId, transferAmount,
                transactionController.getNameOfBank(senderAccountId),
                transactionController.getNameOfBank(receivingAccountNumber), receivingAccountNumber);
    }


    private static Integer nextValidInt() {
        while (true) {
            try {
                System.out.print("Enter bank number: ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Enter an integer.");
                scanner.next();
            }
        }
    }

}