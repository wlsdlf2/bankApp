package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BankApplication {
    static final Scanner in = new Scanner(System.in);
    static final BankSystem banksystem = new BankSystem();

    public static void main(String[] args) {

        boolean run = true;
        while(run) {
            System.out.println("===========================================================================");
            System.out.println("1.계좌 생성 | 2.계좌 관리 | 3.계좌 찾기 | 4.모든 계좌 목록 조회(관리자 메뉴) | 5.종료");
            System.out.println("===========================================================================");
            System.out.print("원하시는 메뉴를 선택하세요 : ");

            int selectMenu = in.nextInt();

            switch (selectMenu) {
                case 1:
                    System.out.println("=============================");
                    System.out.print("예금주님의 성함을 입력하세요 : ");
                    String ownerName = in.next();
                    System.out.print("예금하실 금액을 입력하세요 : ");
                    long depositAmount = in.nextLong();
                    System.out.print("계좌 비밀번호를 입력하세요 : ");
                    String accountPassword = in.next();
                    System.out.println("=============================");

                    banksystem.createBankAccount(ownerName, accountPassword, depositAmount);

                    break;
                case 2:
                    System.out.println("=====================================");
                    System.out.print("계좌번호를 입력하세요 : ");
                    String accountNumber = in.next();
                    System.out.print("비밀번호를 입력하세요 : ");
                    String password = in.next();
                    System.out.println("=====================================");

                    BankAccount account = banksystem.validateAccount(accountNumber, password);

                    if (account != null) {
                        accountManageMenu(account);
                    }

                    break;
                case 3:
                    System.out.println("=====================================");
                    System.out.println("1.계좌 찾기 | 2.비밀번호 찾기");
                    System.out.print("원하는 메뉴를 선택하세요 : ");
                    int selectMenu2 = in.nextInt();
                    System.out.println("=====================================");
                    if (selectMenu2 == 1) {
                        System.out.print("이름을 입력하세요 : ");
                        String name = in.next();

                        System.out.println(banksystem.findAccountNumberByName(name));
                    } else if (selectMenu2 == 2) {
                        System.out.print("계좌번호를 입력하세요 : ");
                        String accNumber = in.next();
                        System.out.print("이름을 입력하세요 : ");
                        String name = in.next();

                        System.out.println(banksystem.findPassword(accNumber, name));
                    }

                    break;
                case 4:
                    System.out.println("=============================");
                    System.out.print("관리자 비밀번호를 입력하세요 : ");
                    String managerPw = in.next();
                    System.out.println("=============================");

                    banksystem.validateManagerPw(managerPw);

                    break;
                case 5: run = false;
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    static void accountManageMenu(BankAccount bankAccount) {
        boolean state = true;
        while (state) {
            if (bankAccount != null) {
                System.out.println();
                System.out.println(bankAccount.getAccountNumber());
                System.out.println("잔고 : " + bankAccount.getBalance());
                System.out.println("==========================================================");
                System.out.println("1. 입금 | 2. 출금 | 3. 계좌 이체 | 4. 거래내역 조회 | 5. 계좌 삭제 | 6. 처음으로");
                System.out.println("==========================================================");
                System.out.print("원하시는 메뉴를 선택하세요 : ");
                int selectMenu = in.nextInt();

                switch (selectMenu) {
                    case 1 :
                        System.out.print("입금하실 금액을 입력하세요 : ");
                        long depositAmount = in.nextLong();

                        if (bankAccount.deposit(depositAmount)) {
                            System.out.println("입금 완료되었습니다.");
                            //거래내역 생성
                            Transaction deposit = new Transaction("입금", depositAmount, bankAccount.getOwner());
                            bankAccount.addTransactionHistory(deposit);
                        } else {
                            System.out.println("입금 실패하였습니다.");
                        }
                        break;
                    case 2 :
                        System.out.print("출금하실 금액을 입력하세요 : ");
                        long withdrawalAmount = in.nextLong();

                        if (bankAccount.withdraw(withdrawalAmount)) {
                            System.out.println("출금 완료했습니다.");
                            System.out.println("출금액 : " + withdrawalAmount + "원");
                            // 거래내역 생성
                            Transaction withdraw = new Transaction("출금", withdrawalAmount, bankAccount.getOwner());
                            bankAccount.addTransactionHistory(withdraw);
                        } else {
                            System.out.println("잔액이 부족합니다.");
                        }
                        break;
                    case 3 :
                        System.out.print("이체할 계좌번호를 입력하세요 : ");
                        String targetAccountNumber = in.next();
                        System.out.print("이체할 금액을 입력하세요 : ");
                        long amount = in.nextLong();

                        if (bankAccount.withdraw(amount)) {
                            BankAccount targetAccount = banksystem.isAccountNumber(targetAccountNumber);
                            if (targetAccount != null) {
                                if (targetAccount.deposit(amount)) {
                                    System.out.println("이체가 완료되었습니다.");
                                    // 거래내역 생성
                                    Transaction withdraw = new Transaction("출금", amount, targetAccount.getOwner());
                                    Transaction deposit = new Transaction("입금", amount, bankAccount.getOwner());
                                    bankAccount.addTransactionHistory(withdraw);
                                    targetAccount.addTransactionHistory(deposit);
                                } else {
                                    System.out.println("이체중에 장애가 발생했습니다.");
                                }
                            } else {
                                System.out.println("존재하지않는 계좌입니다.");
                            }
                        } else {
                            System.out.println("잔액이 부족합니다.");
                        }
                        break;
                    case 4 :
                        System.out.println("최근 거래 내역");
                        // 최근 거래 내역부터 출력
                        List<Transaction> histories = bankAccount.getTransactionHistory();

                        for (int i = histories.size() -1; i >= 0; i--) {
                            Transaction transaction = histories.get(i);
                            System.out.println(transaction.getTransactionType() + "    " +
                                    transaction.getAmount() + " | " +
                                    transaction.getTransactor());
                            System.out.println();
                        }
                        break;
                    case 5 :
                        System.out.println("정말 계좌를 삭제하시겠습니까?(Y/N)");
                        char answer = in.next().charAt(0);
                        if (answer == 'Y') {
                            banksystem.deleteAccount(bankAccount);
                            state = false;
                        }
                        break;
                    case 6 :
                        state = false;
                        break;
                }

            }
        }
    }

}