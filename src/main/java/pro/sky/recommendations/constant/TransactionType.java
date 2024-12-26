package pro.sky.recommendations.constant;

import java.util.Set;

public class TransactionType {
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";

    public static final Set<String> TRANSACTION_TYPES = Set.of(DEPOSIT, WITHDRAW);
}
