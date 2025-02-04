package pro.sky.recommendations.recommendation.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Модель, представляющая запрос для рекомендации.
 * <p>
 * Этот класс содержит информацию о запросе, связанном с рекомендацией, включая аргументы запроса и его строковое представление.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class Query {
    private UUID id;

    private Recommendation recommendation;

    private String query;

    private String[] arguments;

    private Boolean negate;

    /**
     * Преобразует массив аргументов в строку для сохранения в базу данных.
     *
     * @return строка, содержащая все аргументы, разделённые пробелами.
     */
    public String argsToString() {
        return StringUtils.join(this.arguments, " ");
    }

    /**
     * Преобразует строку с аргументами в массив аргументов.
     *
     * @param args строка, содержащая аргументы, разделённые пробелами.
     * @return объект {@link Query} с преобразованными аргументами.
     */
    public Query stringToArgs(String args) {
        this.arguments = args.split(" ");
        return this;
    }
}
