package pro.sky.recommendations.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, когда рекомендация не найдена.
 * Этот класс расширяет {@link RuntimeException} и автоматически вызывает ошибку с кодом статуса 404 (NOT_FOUND) при возникновении исключения.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecommendationNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения {@link RecommendationNotFoundException}.
     * Создаёт новое исключение без сообщения.
     */
    public RecommendationNotFoundException() {
    }
}
