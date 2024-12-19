package pro.sky.recommendations.modelRecommendation;

import pro.sky.recommendations.model.Transaction;

import java.util.List;


public abstract class Debit {
    public abstract boolean hasProduct(List<Transaction> transactions);

    public abstract int totalDeposit(List<Transaction> transactions);

    public abstract int totalWithdraw(List<Transaction> transactions);
}
