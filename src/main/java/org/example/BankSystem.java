package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BankSystem {
    private List<BankAccount> bankAccounts;
    private final String managerPw = "1234";

    public BankSystem() {
        this.bankAccounts = new ArrayList<>();
    }

    // 계좌 생성
    public void createBankAccount(String owner, String password, long balance) {
        String accountNumber = generateAccountNumber();

        while (isAccountNumber(accountNumber) != null) {
            accountNumber = generateAccountNumber();
        }

        BankAccount account = new BankAccount(balance, accountNumber, owner, password);

        bankAccounts.add(account);

        System.out.println("계좌가 성공적으로 개설되었습니다.");
        System.out.println("계좌번호 : " + account.getAccountNumber());
        System.out.println("예금주 : " + account.getOwner());
        System.out.println("잔고 : " + account.getBalance());

    }

    // 계좌번호 생성
    private String generateAccountNumber() {
        Random random = new Random();

        String part1 = String.format("%06d", random.nextInt(1000000));
        String part2 = String.format("%02d", random.nextInt(100));
        String part3 = String.format("%06d", random.nextInt(1000000));

        return part1 + "-" + part2 + "-" + part3; // 계좌번호 반환(xxxxxx-xx-xxxxxx)
    }

    // 일치하는 계좌가 있는지 탐색
    public BankAccount isAccountNumber(String accountNumber) {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountNumber().equals(accountNumber)) {
                return bankAccount;
            }
        }
        return null;
    }

    // 계좌번호와 비밀번호가 일치하는지 검사
    public BankAccount validateAccount(String accountNumber, String password) {
        // 모든 계좌를 순회하며 일치하는 계좌 탐색
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountNumber().equals(accountNumber) && bankAccount.getPassword().equals(password)) {
                System.out.println("환영합니다! " + bankAccount.getOwner() + "님");
                return bankAccount;
            }
        }
        System.out.println("계좌번호 또는 비밀번호가 일치하지 않습니다.");
        return null;
    }

    // 관리자 비밀번호 체크
    public void validateManagerPw(String pw) {
        if (pw.equals(managerPw)) {
            showAllAccount();
        }
    }

    // 계좌 삭제
    public void deleteAccount(BankAccount account) {
        bankAccounts.remove(account);
    }

    // 모든 계좌 목록
    public void showAllAccount() {
        System.out.println("전체 계좌 목록");

        for (BankAccount account : bankAccounts) {
            System.out.println(account.getAccountNumber());
            System.out.println("예금주 : " + account.getOwner());
            System.out.println();
        }
    }

    // 계좌번호 찾기
    public String findAccountNumberByName(String name) {
        for (BankAccount account : bankAccounts) {
            if (account.getOwner().equals(name)) {
                return account.getAccountNumber();
            }
        }
        return null;
    }

    // 비밀번호 찾기
    public String findPassword(String accountNumber, String ownerName) {
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber().equals(accountNumber) && account.getOwner().equals(ownerName)) {
                return account.getPassword();
            }
        }
        return null;
    }
}
