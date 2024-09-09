package org.example;

public class Transaction {
    private final String transactionType; // 거래유형(입금/출금)
    private final long amount; // 거래액
    private final String transactor; // 거래를 수행한 사람(입금의 경우 입금한 주체, 출금의 경우 돈을 받는 주체)

    public Transaction(String transactionType, long amount, String transactor) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactor = transactor;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public long getAmount() {
        return amount;
    }

    public String getTransactor() {
        return transactor;
    }
}
