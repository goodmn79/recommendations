package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Перечисление типов продуктов.
 * Содержит различные типы продуктов: дебетовый, кредитный, сберегательный и инвестиционный.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@ToString
@RequiredArgsConstructor
public enum ProductType {


    DEBIT("DEBIT"),


    CREDIT("CREDIT"),


    SAVING("SAVING"),


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
