package pro.sky.recommendations.constant;


import java.util.Set;

public class ComparisonOperator {
    public static final String EQ = "=";
    public static final String GT = ">";
    public static final String GE = ">=";
    public static final String LT = "<";
    public static final String LE = "<=";

    public static final Set<String> COMPARISON_OPERATORS;

    static {
        COMPARISON_OPERATORS = Set.of(EQ, GT, GE, LT, LE);
    }
}
