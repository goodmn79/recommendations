/*
Файл шаблонов SQL-запросов
Powered by ©AYE.team
 */

package pro.sky.recommendations.constant;

public final class QueryPattern {


    public static final String USER_OF_SQL = "SELECT COUNT(*) > 0 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'";
    public static final String ACTIVE_USER_OF_SQL = "SELECT COUNT(*) >= 5 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'";
    public static final String TRANSACTION_SUM_COMPARE_SQL = "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-' AND t.TYPE = '-?-' GROUP BY u.ID HAVING SUM(t.AMOUNT) -?- -?-)";
    public static final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW_SQL = "SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p on p.ID = t.PRODUCT_ID WHERE USER_ID = ? AND p.TYPE = '-?-' GROUP BY u.ID HAVING SUM(CASE WHEN t.TYPE = 'DEPOSIT' THEN t.AMOUNT ELSE 0 END) -?- SUM(CASE WHEN t.TYPE = 'WITHDRAW' THEN t.AMOUNT ELSE 0 END))";
}

