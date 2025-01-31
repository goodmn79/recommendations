package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Перечисление операторов сравнения.
 * Содержит операторы для различных типов сравнений: равенство, больше, больше или равно, меньше, меньше или равно.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@ToString
@RequiredArgsConstructor
public enum ComparisonOperator {


    EQ("="),

    GT(">"),

    GE(">="),

    LT("<"),

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
