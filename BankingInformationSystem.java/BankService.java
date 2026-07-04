package com.bank.service;

import com.bank.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BankService {
    // In-memory persistence for the prototype session
    private Map<String, User> userDatabase = new HashMap<>();
    private Random random = new Random();

    /**
     * Registers a new user and generates a unique account number.
     * @return The newly created User object.
     */
    public User registerUser(String name, String address, String contactNumber, double initialDeposit, String password) {
        String accountNumber = generateUniqueAccountNumber();
        
        User newUser = new User(accountNumber, name, address, contactNumber, initialDeposit, password);
        userDatabase.put(accountNumber, newUser);
        
        return newUser;
    }

    /**
     * Generates a 10-digit random account number that is unique in the system.
     */
    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            // Generate a random 10-digit number
            long number = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
            accountNumber = String.valueOf(number);
        } while (userDatabase.containsKey(accountNumber)); // Ensure uniqueness
        
        return accountNumber;
    }

    // This method will be useful for subsequent modules (e.g., login, transactions)
    public User getUserByAccountNumber(String accountNumber) {
        return userDatabase.get(accountNumber);
    }

    /**
     * Authenticates a user based on account number and password.
     * @return The User object if successful, null otherwise.
     */
    public User authenticateUser(String accountNumber, String password) {
        User user = userDatabase.get(accountNumber);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Deposits money into the user's account.
     */
    public void deposit(User user, double amount) {
        if (amount > 0) {
            user.setBalance(user.getBalance() + amount);
            user.addTransaction(new com.bank.model.Transaction("Deposit", amount, "Cash Deposit"));
        }
    }

    /**
     * Withdraws money from the user's account if sufficient funds exist.
     * @return true if successful, false if insufficient funds.
     */
    public boolean withdraw(User user, double amount) {
        if (amount > 0 && user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            user.addTransaction(new com.bank.model.Transaction("Withdrawal", amount, "Cash Withdrawal"));
            return true;
        }
        return false;
    }

    /**
     * Transfers money from one user to another.
     * @return true if successful, false otherwise.
     */
    public boolean transfer(User sender, String receiverAccountNumber, double amount) {
        if (amount <= 0 || sender.getBalance() < amount) {
            return false;
        }
        
        User receiver = userDatabase.get(receiverAccountNumber);
        if (receiver == null || sender.getAccountNumber().equals(receiverAccountNumber)) {
            return false;
        }
        
        // Process transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        
        sender.addTransaction(new com.bank.model.Transaction("Transfer Out", amount, "To A/C: " + receiverAccountNumber));
        receiver.addTransaction(new com.bank.model.Transaction("Transfer In", amount, "From A/C: " + sender.getAccountNumber()));
        
        return true;
    }
}
