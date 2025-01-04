package pro.sky.recommendations.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum QueryType {
    USER_OF("SELECT COUNT(*) > 0 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'"),
    ACTIVE_USER_OF("SELECT COUNT(*) >= 5 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'"),
    TRANSACTION_SUM_COMPARE("SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-' AND t.TYPE = '-?-' GROUP BY u.ID HAVING SUM(t.AMOUNT) -?- -?-)"),
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p on p.ID = t.PRODUCT_ID WHERE USER_ID = ? AND p.TYPE = '-?-' GROUP BY u.ID HAVING SUM(CASE WHEN t.TYPE = 'DEPOSIT' THEN t.AMOUNT ELSE 0 END) -?- SUM(CASE WHEN t.TYPE = 'WITHDRAW' THEN t.AMOUNT ELSE 0 END))");

    private final String query;

    public static boolean hasType(String transactionType) {
        for (QueryType type : QueryType.values()) {
            if (type.name().equals(transactionType)) return true;
        }
        return false;
    }
}
