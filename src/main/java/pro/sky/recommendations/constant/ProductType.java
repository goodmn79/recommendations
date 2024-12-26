package pro.sky.recommendations.constant;

import java.util.Set;

public final class ProductType {
    public static final String DEBIT = "DEBIT";
    public static final String SAVING = "SAVING";
    public static final String CREDIT = "CREDIT";
    public static final String INVEST = "INVEST";

    public static final Set<String> PRODUCT_TYPES = Set.of(DEBIT, SAVING, CREDIT, INVEST);
}
