package pro.sky.recommendations.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TransactionType {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    private final String value;

    public static boolean hasType(String transactionType) {
        for (TransactionType type : TransactionType.values()) {
            if (type.value.equals(transactionType)) return true;
        }
        return false;
    }
}
