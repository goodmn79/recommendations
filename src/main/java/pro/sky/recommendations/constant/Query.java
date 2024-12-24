package pro.sky.recommendations.constant;

import java.util.Set;

public class Query {
    private static final String USER_OF = "USER_OF";
    private static final String ACTIVE_USER_OF = "ACTIVE_USER_OF";
    private static final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE";
    private static final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";

    public static final Set<String> QUERY;

    static {
        QUERY = Set.of(USER_OF, ACTIVE_USER_OF, TRANSACTION_SUM_COMPARE, TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW);
    }
}
