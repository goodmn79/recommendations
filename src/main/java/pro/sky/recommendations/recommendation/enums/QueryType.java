package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import pro.sky.recommendations.recommendation.exception.InvalidQueryDataException;

/**
 * Перечисление типов запросов, содержащих соответствующие шаблоны SQL-запросов.
 * Каждый тип запроса представляет собой SQL-шаблон, который выполняет различные операции с базой данных.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@RequiredArgsConstructor
public enum QueryType {

    /**
     * Тип запроса, проверяющий использование клиентом определённого продукта.
     */
    USER_OF("SELECT COUNT(*) > 0 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'"),

    /**
     * Тип запроса, проверяющий активное использование клиентом определённого продукта.
     */
    ACTIVE_USER_OF("SELECT COUNT(*) >= 5 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-'"),

    /**
     * Тип запроса, проверяющий соответствие суммы транзакций определённого продукта заданным параметрам.
     */
    TRANSACTION_SUM_COMPARE("SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID WHERE t.USER_ID = ? AND p.TYPE = '-?-' AND t.TYPE = '-?-' GROUP BY u.ID HAVING SUM(t.AMOUNT) -?- -?-)"),

    /**
     * Тип запроса, проверяющий отношение суммы пополнения к сумме списания для определённого продукта.
     */
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("SELECT EXISTS (SELECT 1 FROM TRANSACTIONS t JOIN USERS u ON u.ID = t.USER_ID JOIN PRODUCTS p on p.ID = t.PRODUCT_ID WHERE USER_ID = ? AND p.TYPE = '-?-' GROUP BY u.ID HAVING SUM(CASE WHEN t.TYPE = 'DEPOSIT' THEN t.AMOUNT ELSE 0 END) -?- SUM(CASE WHEN t.TYPE = 'WITHDRAW' THEN t.AMOUNT ELSE 0 END))");

    private final String queryPattern;

    /**
     * Возвращает строковое представление SQL-шаблона для данного типа запроса.
     * Переопределённый метод {@code toString}.
     *
     * @return строка, представляющая SQL-шаблон.
     */
    @Override
    public String toString() {
        return queryPattern;
    }

    /**
     * Возвращает SQL-шаблон для заданного типа запроса.
     *
     * @param query имя типа запроса.
     * @return SQL-шаблон, соответствующий типу запроса.
     * @throws InvalidQueryDataException если тип запроса не найден.
     */
    public static String getQueryPattern(String query) {
        for (QueryType type : QueryType.values()) {
            if (type.name().equals(query)) {
                return type.queryPattern;
            }
        }
        throw new InvalidQueryDataException();
    }

    /**
     * Проверяет, существует ли тип запроса с заданным именем.
     *
     * @param queryType имя типа запроса.
     * @return {@code true}, если тип запроса существует, {@code false} в противном случае.
     */
    public static boolean hasType(String queryType) {
        for (QueryType type : QueryType.values()) {
            if (type.name().equals(queryType)) return true;
        }
        return false;
    }
}
