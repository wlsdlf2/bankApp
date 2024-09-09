package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccount {
    private long balance; // 잔고
    private final String accountNumber; // 계좌번호
    private String owner; // 계좌주 이름
    private String password; // 비밀번호
    private List<Transaction> transactionHistories = new ArrayList<>(); // 거래내역

    public BankAccount(long balance, String accountNumber, String owner, String password) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.password = password;

        Transaction transaction = new Transaction("입금", balance, owner);
        addTransactionHistory(transaction);
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistories;
    }

    public void addTransactionHistory(Transaction transaction) {
        transactionHistories.add(transaction);
    }

    public boolean deposit(long amount) {
        if (amount >= 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}
