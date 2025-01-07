/*
Объект передачи и валидации данных для создания SQL-запросов для динамических правил рекомендаций
Powered by ©AYE.team
 */

package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import pro.sky.recommendations.enums.ComparisonOperator;
import pro.sky.recommendations.enums.ProductType;
import pro.sky.recommendations.enums.QueryType;
import pro.sky.recommendations.enums.TransactionType;
import pro.sky.recommendations.exception.InvalidQueryDataException;

@Data
@Accessors(chain = true)
public class QueryData {
    private String query;
    private String[] arguments;
    private Boolean negate;

    public QueryData setQuery(String query) {
        if (QueryType.hasType(query)) {
            this.query = query;
            return this;
        }
        throw new InvalidQueryDataException();
    }

    public QueryData setArguments(String[] arguments) {
        if (validArguments(arguments)) {
            this.arguments = arguments;
            return this;
        }
        throw new InvalidQueryDataException();
    }

    public QueryData setNegate(Boolean negate) {
        if (negate == null) {
            throw new InvalidQueryDataException();
        }
        this.negate = negate;
        return this;
    }

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
