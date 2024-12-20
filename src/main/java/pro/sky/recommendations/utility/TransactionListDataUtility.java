package pro.sky.recommendations.utility;

import pro.sky.recommendations.model.Transaction;

import java.util.List;


public class TransactionListDataUtility {
    public static boolean productUsage(List<Transaction> transactions, String productType) {
        return transactions.stream()
                .anyMatch(transaction -> transaction.getProduct().getType().equals(productType));
    }

    public static int totalDeposit(List<Transaction> transactions, String productType) {
        return transactions.stream()
                .filter(transaction -> transaction.getProduct().getType().equals(productType))
                .filter(transaction -> transaction.getType().equals("DEPOSIT"))
                .mapToInt(Transaction::getAmount)
                .sum();
    }

    public static int totalWithdraw(List<Transaction> transactions, String productType) {
        return transactions.stream()
                .filter(transaction -> transaction.getProduct().getType().equals(productType))
                .filter(transaction -> transaction.getType().equals("WITHDRAW"))
                .mapToInt(Transaction::getAmount)
                .sum();
    }
}
