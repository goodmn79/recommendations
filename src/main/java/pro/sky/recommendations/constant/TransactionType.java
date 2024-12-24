package pro.sky.recommendations.constant;

import java.util.Set;

public class TransactionType {
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";

    public static final Set<String> TRANSACTION_TYPE;

    static {
        TRANSACTION_TYPE =
                Set.of(DEPOSIT, WITHDRAW);
    }
}
