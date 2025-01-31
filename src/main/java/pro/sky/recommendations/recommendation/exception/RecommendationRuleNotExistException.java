package pro.sky.recommendations.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, когда правило рекомендации не существует.
 * <p>
 * Этот класс расширяет {@link RuntimeException} и автоматически вызывает ошибку с кодом статуса 404 (NOT_FOUND) при возникновении исключения.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecommendationRuleNotExistException extends RuntimeException {

    /**
     * Конструктор исключения {@link RecommendationRuleNotExistException}.
     * <br>Создаёт новое исключение без сообщения.
     */
    public RecommendationRuleNotExistException() {
    }
}
