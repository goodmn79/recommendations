package pro.sky.recommendations.recommendation.enums;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Перечисление типов транзакций.
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@ToString
@RequiredArgsConstructor
public enum TransactionType {

    /**
     * Тип транзакции "DEPOSIT".
     */
    DEPOSIT("DEPOSIT"),

    /**
     * Тип транзакции "WITHDRAW".
     */
    WITHDRAW("WITHDRAW");

    private final String value;

    /**
     * Проверяет, существует ли тип транзакции с заданным значением.
     *
     * @param transactionType строка, представляющая тип транзакции.
     * @return {@code true}, если тип транзакции существует, {@code false} в противном случае.
     */
    public static boolean hasType(String transactionType) {
        for (TransactionType type : TransactionType.values()) {
            if (type.value.equals(transactionType)) return true;
        }
        return false;
    }
}
