/*
Файл содержащий информацию о типах запросов и соответствующих шаблонах SQL-запросов
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import pro.sky.recommendations.recommendation.exception.InvalidQueryDataException;

@RequiredArgsConstructor
public enum QueryType {
    // Тип, проверяющий использование клиентом определённого продукта
    USER_OF("SELECT COUNT(*) > 0 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'"),
    // Тип, проверяющий активное использование клиентом определённого продукта
    ACTIVE_USER_OF("SELECT COUNT(*) >= 5 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'"),
    // Тип, проверяющий соответствие суммы транзакций определённого продукта заданным параметрам
    TRANSACTION_SUM_COMPARE("SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-' AND t.TYPE = '-?-' GROUP BY u.ID HAVING SUM(t.AMOUNT) -?- -?-)"),
    // Тип, проверяющий отношение суммы пополнения к сумме списания определённого продукта
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p on p.ID = t.PRODUCT_ID WHERE USER_ID = ? AND p.TYPE = '-?-' GROUP BY u.ID HAVING SUM(CASE WHEN t.TYPE = 'DEPOSIT' THEN t.AMOUNT ELSE 0 END) -?- SUM(CASE WHEN t.TYPE = 'WITHDRAW' THEN t.AMOUNT ELSE 0 END))");

    private final String queryPattern;

    @Override
    public String toString() {
        return queryPattern;
    }

    public static String getQueryPattern(String query) {
        for (QueryType type : QueryType.values()) {
            if (type.name().equals(query)) {
                return type.queryPattern;
            }
        }
        throw new InvalidQueryDataException();
    }

    public static boolean hasType(String queryType) {
        for (QueryType type : QueryType.values()) {
            if (type.name().equals(queryType)) return true;
        }
        return false;
    }
}
