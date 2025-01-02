/*
Объект передачи и валидации данных для создания SQL-запросов для динамических правил рекомендаций
Powered by ©AYE.team
 */

package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.constant.ComparisonOperator;
import pro.sky.recommendations.constant.ProductType;
import pro.sky.recommendations.constant.TransactionType;
import pro.sky.recommendations.exception.InvalidQueryDataException;

import static pro.sky.recommendations.constant.QueryType.*;

@Component
@Data
@Accessors(chain = true)
public class QueryData {
    private String query;
    private String[] arguments;
    private Boolean negate;

    public void validate() {
        if (!QUERY_TYPES.containsKey(this.query)
                || !validArgs()
                || this.negate == null) {
            throw new InvalidQueryDataException();
        }
    }

    private boolean validArgs() {

        return switch (this.query) {
            case (USER_OF), (ACTIVE_USER_OF) -> arguments.length == 1
                    && ProductType.PRODUCT_TYPES.contains(arguments[0]);
            case (TRANSACTION_SUM_COMPARE) -> arguments.length == 4
                    && ProductType.PRODUCT_TYPES.contains(arguments[0])
                    && TransactionType.TRANSACTION_TYPES.contains(arguments[1])
                    && ComparisonOperator.COMPARISON_OPERATORS.contains(arguments[2])
                    && isPositiveNumber(arguments[3]);
            case (TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW) -> arguments.length == 2
                    && ProductType.PRODUCT_TYPES.contains(arguments[0])
                    && ComparisonOperator.COMPARISON_OPERATORS.contains(arguments[1]);
            default -> false;
        };
    }

    public boolean isPositiveNumber(String num) {
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
