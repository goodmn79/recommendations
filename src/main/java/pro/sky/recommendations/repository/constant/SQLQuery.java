package pro.sky.recommendations.repository.constant;

public final class SQLQuery {
    public static final String FIND_RECOMMENDATION_BY_NAME = "SELECT * FROM RECOMMENDATIONS WHERE NAME = ?";
    public static final String FIND_ALL_TRANSACTION_BY_USER_ID = "SELECT t.ID AS transaction_id, t.TYPE AS transaction_type, t.AMOUNT, u.ID AS user_id, u.USERNAME, u.FIRST_NAME, u.LAST_NAME, p.ID AS product_id, p.NAME, p.TYPE AS product_type FROM TRANSACTIONS t JOIN USERS u ON t.USER_ID = u.ID JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID WHERE u.ID = ?";
    public static final String FIND_USER_BY_ID = "SELECT * FROM USERS WHERE ID = ?";

    public static final String FIND_USER_BY_RULES_FOR_INVEST500 =
            "SELECT u.ID AS user_id FROM Users u JOIN Transactions t ON u.ID = t.user_id JOIN Products p ON t.product_id = p.ID GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 AND SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > 1000";

    public static final String FIND_USER_BY_RULES_FOR_SIMPLE_CREDIT =
            "SELECT u.ID AS user_id FROM Users u JOIN Transactions t ON u.ID = t.user_id JOIN Products p ON t.product_id = p.ID GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'CREDIT' THEN 1 ELSE 0 END) = 0 AND (SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END)) AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) > 100000";

    public static final String FIND_USER_BY_RULES_FOR_SIMPLE_TOP_SAVING =
            "SELECT u.ID AS user_id FROM Users u JOIN Transactions t ON u.ID = t.user_id JOIN Products p ON t.product_id = p.ID GROUP BY u.ID HAVING SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >= 50000 OR SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >= 50000 AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END)";
}

