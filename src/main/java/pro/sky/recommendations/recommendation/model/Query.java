/*
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Query {
    private UUID id;
    private Recommendation recommendation;
    private String query;
    private String[] arguments;
    private Boolean negate;

    // Преобразование массива аргументов в строку для сохранения в базу данных
    public String argsToString() {
        return StringUtils.join(this.arguments, " ");
    }

    // Преобразование строки с аргументами в массив аргументов
    public Query stringToArgs(String args) {
        this.arguments = args.split(" ");
        return this;
    }
}
