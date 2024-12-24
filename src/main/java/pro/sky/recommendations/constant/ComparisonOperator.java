package pro.sky.recommendations.constant;

import java.util.Set;

public class ComparisonOperator {
    public static final String EQUALS = "=";
    public static final String GREATER_THAN = ">";
    public static final String GREATER_THAN_EQUALS = ">=";
    public static final String LESS_THAN = "<";
    public static final String LESS_THAN_EQUALS = "<=";

    public static final Set<String> COMPARISON_OPERATOR;

    static {
        COMPARISON_OPERATOR =
                Set.of(EQUALS, GREATER_THAN, GREATER_THAN_EQUALS, LESS_THAN, LESS_THAN_EQUALS);

    }
}
