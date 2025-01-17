package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum ComparisonOperator {
    EQ("="),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<=");

    private final String value;

    public static boolean hasOperator(String comparisonOperator) {
        for (ComparisonOperator operator : ComparisonOperator.values()) {
            if (operator.value.equals(comparisonOperator)) {
                return true;
            }
        }
        return false;
    }
}
