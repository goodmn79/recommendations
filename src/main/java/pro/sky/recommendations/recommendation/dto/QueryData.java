package pro.sky.recommendations.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import pro.sky.recommendations.recommendation.enums.ComparisonOperator;
import pro.sky.recommendations.recommendation.enums.ProductType;
import pro.sky.recommendations.recommendation.enums.QueryType;
import pro.sky.recommendations.recommendation.enums.TransactionType;
import pro.sky.recommendations.recommendation.exception.InvalidQueryDataException;

/**
 * DTO класс для передачи и валидации данных при создании SQL-запросов
 * для динамических правил рекомендаций.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class QueryData {
    /**
     * Тип запроса из перечисления QueryType
     */
    private String query;

    /**
     * Массив аргументов для запроса
     */
    private String[] arguments;

    /**
     * Флаг отрицания условия запроса
     */
    private Boolean negate;

    /**
     * Устанавливает тип запроса после проверки его валидности.
     *
     * @param query Тип запроса
     * @return Текущий объект QueryData
     * @throws InvalidQueryDataException если тип запроса невалидный
     */
    public QueryData setQuery(String query) {
        if (QueryType.hasType(query)) {
            this.query = query;
            return this;
        }
        throw new InvalidQueryDataException();
    }

    /**
     * Устанавливает аргументы запроса после проверки их валидности.
     *
     * @param arguments Массив аргументов
     * @return Текущий объект QueryData
     * @throws InvalidQueryDataException если аргументы невалидны
     */
    public QueryData setArguments(String[] arguments) {
        if (validArguments(arguments)) {
            this.arguments = arguments;
            return this;
        }
        throw new InvalidQueryDataException();
    }

    /**
     * Устанавливает флаг отрицания условия запроса.
     *
     * @param negate Флаг отрицания
     * @return Текущий объект QueryData
     * @throws InvalidQueryDataException если значение null
     */
    public QueryData setNegate(Boolean negate) {
        if (negate == null) {
            throw new InvalidQueryDataException();
        }
        this.negate = negate;
        return this;
    }

    /**
     * Проверяет валидность аргументов в зависимости от типа запроса.
     *
     * @param arguments Массив аргументов для проверки
     * @return true если аргументы валидны, false в противном случае
     */
    private boolean validArguments(String[] arguments) {
        return switch (this.query) {
            case ("USER_OF"), ("ACTIVE_USER_OF") -> arguments.length == 1
                    && ProductType.hasType(arguments[0]);
            case ("TRANSACTION_SUM_COMPARE") -> arguments.length == 4
                    && ProductType.hasType(arguments[0])
                    && TransactionType.hasType(arguments[1])
                    && ComparisonOperator.hasOperator(arguments[2])
                    && isPositiveNumber(arguments[3]);
            case ("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW") -> arguments.length == 2
                    && ProductType.hasType(arguments[0])
                    && ComparisonOperator.hasOperator(arguments[1]);
            default -> false;
        };
    }

    /**
     * Проверяет, является ли строка положительным числом.
     *
     * @param num Строка для проверки
     * @return true если строка представляет положительное число, false в противном случае
     */
    private boolean isPositiveNumber(String num) {
        if (StringUtils.isNumeric(num)) {
            try {
                return Integer.parseInt(num) > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
