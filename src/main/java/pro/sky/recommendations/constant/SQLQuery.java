package pro.sky.recommendations.constant;

public final class SQLQuery {
    public static final String FIND_RECOMMENDATION_BY_NAME = "SELECT * FROM RECOMMENDATIONS WHERE NAME = ?";
    public static final String FIND_ALL_TRANSACTION_BY_USER_ID = "SELECT t.ID AS transaction_id, t.TYPE AS transaction_type, t.AMOUNT, u.ID AS user_id, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, p.ID AS product_id, p.NAME, p.TYPE AS product_type FROM TRANSACTIONS t JOIN USERS u ON t.USER_ID = u.ID JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID WHERE u.ID = ?";
    public static final String FIND_USER_BY_ID = "SELECT * FROM USERS WHERE ID = ?";

    public static final String FIND_USER_BY_RULES_FOR_INVEST500 =
            "SELECT EXISTS (SELECT 1 FROM Transactions t JOIN Users u ON  t.USER_ID = u.ID JOIN Products p ON p.ID = t.PRODUCT_ID WHERE u.ID = ? GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 AND SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > 1000)";

    public static final String FIND_USER_BY_RULES_FOR_SIMPLE_CREDIT =
            "SELECT EXISTS (SELECT 1 FROM Transactions t JOIN Users u ON  t.USER_ID = u.ID JOIN Products p ON p.ID = t.PRODUCT_ID WHERE u.ID = ? GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'CREDIT' THEN 1 ELSE 0 END) = 0 AND (SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END)) AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) > 100000)";

    public static final String FIND_USER_BY_RULES_FOR_SIMPLE_TOP_SAVING =
            "SELECT EXISTS (SELECT 1 FROM Transactions t JOIN Users u ON  t.USER_ID = u.ID JOIN Products p ON p.ID = t.PRODUCT_ID WHERE u.ID = ? GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND (SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >= 50000 OR SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >= 50000) AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END))";

    public  static final  String USER_OF_SQL = "SELECT COUNT(*) > 0 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = ?";
    public  static  final String ACTIVE_USER_OF_SQL = "SELECT COUNT(*) >= 5 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = ?";
    public  static  final String TRANSACTION_SUM_COMPARE_SQL = "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = ? GROUP BY u.ID HAVING SUM(t.AMOUNT) ? ?)";
    public  static  final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW_SQL = "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p on p.ID = t.PRODUCT_ID WHERE USER_ID = ? AND p.TYPE = ? GROUP BY u.ID HAVING SUM(CASE WHEN t.TYPE = 'DEPOSIT' THEN t.AMOUNT ELSE 0 END) ? SUM(CASE WHEN t.TYPE = 'WITHDRAW' THEN t.AMOUNT ELSE 0 END))";
}

