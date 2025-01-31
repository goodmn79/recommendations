package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Перечисление операторов сравнения.
 * <p>
 * Содержит операторы для различных типов сравнений: равенство, больше, больше или равно, меньше, меньше или равно.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@ToString
@RequiredArgsConstructor
public enum ComparisonOperator {

    /**
     * Оператор сравнения '='
     */
    EQ("="),

    /**
     * Оператор сравнения '&gt;'
     */
    GT(">"),

    /**
     * Оператор сравнения '&gt;='
     */
    GE(">="),

    /**
     * Оператор сравнения '&lt;'
     */
    LT("<"),

    /**
     * Оператор сравнения '&lt;='
     */
    LE("<=");

    private final String value;

    /**
     * Проверяет, существует ли оператор сравнения с заданным значением.
     *
     * @param comparisonOperator строка, представляющая оператор сравнения.
     * @return {@code true}, если оператор существует, {@code false} в противном случае.
     */
    public static boolean hasOperator(String comparisonOperator) {
        for (ComparisonOperator operator : ComparisonOperator.values()) {
            if (operator.value.equals(comparisonOperator)) {
                return true;
            }
        }
        return false;
    }
}
