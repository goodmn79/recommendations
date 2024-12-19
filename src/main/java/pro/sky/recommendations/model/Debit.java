package pro.sky.recommendations.model;

import java.util.List;


public abstract class Debit {
    public abstract boolean hasProduct(List<Transaction> transactions);

    public abstract int totalDeposit(List<Transaction> transactions);

    public abstract int totalWithdraw(List<Transaction> transactions);
}
