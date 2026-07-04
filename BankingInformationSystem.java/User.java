package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String accountNumber;
    private String name;
    private String address;
    private String contactNumber;
    private double balance;
    private String password;
    private List<Transaction> transactionHistory;

    public User(String accountNumber, String name, String address, String contactNumber, double initialDeposit, String password) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.balance = initialDeposit;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
        if (initialDeposit > 0) {
            this.transactionHistory.add(new Transaction("Deposit", initialDeposit, "Initial Deposit"));
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + "\n" +
               "Name: " + name + "\n" +
               "Address: " + address + "\n" +
               "Contact: " + contactNumber + "\n" +
               "Balance: $" + String.format("%.2f", balance);
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
