package pro.sky.recommendations.model;

import java.util.List;

public class TopSaving extends Debit {

    @Override
    public boolean hasProduct(List<Transaction> transactions) {
        return transactions.stream()
                .anyMatch(transaction -> transaction.getProduct().getType().equals("DEBIT"));
    }

    @Override
    public int totalDeposit(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getProduct().getType().equals("DEBIT"))
                .filter(transaction -> transaction.getType().equals("DEPOSIT"))
                .mapToInt(Transaction::getAmount)
                .sum();
    }

    public int totalDepositForSaving(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getProduct().getType().equals("SAVING"))
                .filter(transaction -> transaction.getType().equals("DEPOSIT"))
                .mapToInt(Transaction::getAmount)
                .sum();
    }

    @Override
    public int totalWithdraw(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getProduct().getType().equals("DEBIT"))
                .filter(transaction -> transaction.getType().equals("WITHDRAW"))
                .mapToInt(Transaction::getAmount)
                .sum();
    }
}