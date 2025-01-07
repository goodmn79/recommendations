package pro.sky.recommendations.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum ProductType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT"),
    SAVING("SAVING"),
    INVEST("INVEST");

    private final String value;

    public static boolean hasType(String productType) {
        for (ProductType type : ProductType.values()) {
            if (type.value.equals(productType)) return true;
        }
        return false;
    }
}
