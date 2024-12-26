package pro.sky.recommendations.constant;

import java.util.Map;

import static pro.sky.recommendations.constant.SQLQuery.*;

public class QueryType {
    public static final String USER_OF = "USER_OF";
    public static final String ACTIVE_USER_OF = "ACTIVE_USER_OF";
    public static final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE";
    public static final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";

    public static final Map<String, String> QUERY_TYPES;

    static {
        QUERY_TYPES = Map.of(
                USER_OF, USER_OF_SQL,
                ACTIVE_USER_OF, ACTIVE_USER_OF_SQL,
                TRANSACTION_SUM_COMPARE, TRANSACTION_SUM_COMPARE_SQL,
                TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW, TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW_SQL);
    }
}
