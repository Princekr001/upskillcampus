package com.bank;

import com.bank.model.User;
import com.bank.service.BankService;

import java.util.Scanner;

public class Main {
    private static BankService bankService = new BankService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        System.out.println("=========================================");
        System.out.println("   Welcome to Banking Information System");
        System.out.println("=========================================");

        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. User Registration");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleRegistration();
                    break;
                case "2":
                    handleLogin();
                    break;
                case "3":
                    exit = true;
                    System.out.println("Thank you for using the Banking Information System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    private static void handleRegistration() {
        System.out.println("\n--- User Registration Form ---");
        
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        
        System.out.print("Enter a secure password: ");
        String password = scanner.nextLine();

        double initialDeposit = -1;
        while (initialDeposit < 0) {
            System.out.print("Enter initial deposit amount (must be >= 0): $");
            try {
                initialDeposit = Double.parseDouble(scanner.nextLine());
                if (initialDeposit < 0) {
                    System.out.println("Error: Deposit amount cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            }
        }

        // Call service to register the user
        User newUser = bankService.registerUser(name, address, contactNumber, initialDeposit, password);

        System.out.println("\n--- Registration Successful! ---");
        System.out.println("Welcome, " + newUser.getName() + "!");
        System.out.println("Your unique Account Number is: " + newUser.getAccountNumber());
        System.out.println("Please keep this account number and your password secure.");
    }

    private static void handleLogin() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter your Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        User user = bankService.authenticateUser(accountNumber, password);

        if (user != null) {
            System.out.println("Login Successful!");
            userDashboard(user);
        } else {
            System.out.println("Error: Invalid Account Number or Password.");
        }
    }

    private static void userDashboard(User user) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("Welcome, " + user.getName() + " | Account No: " + user.getAccountNumber());
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");
            System.out.print("Please enter your choice (1-6): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Current Balance: $" + String.format("%.2f", user.getBalance()));
                    break;
                case "2":
                    handleDeposit(user);
                    break;
                case "3":
                    handleWithdrawal(user);
                    break;
                case "4":
                    handleTransfer(user);
                    break;
                case "5":
                    showTransactionHistory(user);
                    break;
                case "6":
                    logout = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleDeposit(User user) {
        System.out.print("Enter amount to deposit: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount > 0) {
                bankService.deposit(user, amount);
                System.out.println("Successfully deposited $" + String.format("%.2f", amount));
                System.out.println("New Balance: $" + String.format("%.2f", user.getBalance()));
            } else {
                System.out.println("Error: Deposit amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a valid number.");
        }
    }

    private static void handleWithdrawal(User user) {
        System.out.print("Enter amount to withdraw: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount > 0) {
                if (bankService.withdraw(user, amount)) {
                    System.out.println("Successfully withdrew $" + String.format("%.2f", amount));
                    System.out.println("New Balance: $" + String.format("%.2f", user.getBalance()));
                } else {
                    System.out.println("Error: Insufficient funds. Your balance is $" + String.format("%.2f", user.getBalance()));
                }
            } else {
                System.out.println("Error: Withdrawal amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a valid number.");
        }
    }

    private static void handleTransfer(User user) {
        System.out.print("Enter recipient Account Number: ");
        String receiverAccount = scanner.nextLine();
        
        System.out.print("Enter amount to transfer: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount > 0) {
                if (bankService.transfer(user, receiverAccount, amount)) {
                    System.out.println("Successfully transferred $" + String.format("%.2f", amount) + " to account " + receiverAccount);
                    System.out.println("New Balance: $" + String.format("%.2f", user.getBalance()));
                } else {
                    System.out.println("Error: Transfer failed. Check if you have sufficient funds and the recipient account number is correct.");
                }
            } else {
                System.out.println("Error: Transfer amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a valid number.");
        }
    }

    private static void showTransactionHistory(User user) {
        System.out.println("\n--- Transaction History ---");
        java.util.List<com.bank.model.Transaction> history = user.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (com.bank.model.Transaction t : history) {
                System.out.println(t.toString());
            }
        }
        System.out.println("---------------------------");
    }
}
