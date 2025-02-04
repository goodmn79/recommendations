package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Перечисление типов банковских продуктов.
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@ToString
@RequiredArgsConstructor
public enum ProductType {

    /**
     * Тип продукта "DEBIT".
     */
    DEBIT("DEBIT"),

    /**
     * Тип продукта "CREDIT".
     */
    CREDIT("CREDIT"),

    /**
     * Тип продукта "SAVING".
     */
    SAVING("SAVING"),

    /**
     * Тип продукта "INVEST".
     */
    INVEST("INVEST");

    private final String value;

    /**
     * Проверяет, существует ли тип продукта с заданным значением.
     *
     * @param productType строка, представляющая тип продукта.
     * @return {@code true}, если тип продукта существует, {@code false} в противном случае.
     */
    public static boolean hasType(String productType) {
        for (ProductType type : ProductType.values()) {
            if (type.value.equals(productType)) return true;
        }
        return false;
    }
}
