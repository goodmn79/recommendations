package pro.sky.recommendations.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается при неверных данных запроса.
 * Этот класс расширяет {@link RuntimeException} и автоматически вызывает ошибку с кодом статуса 400 (BAD_REQUEST) при возникновении исключения.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQueryDataException extends RuntimeException {

    /**
     * Конструктор исключения {@link InvalidQueryDataException}.
     * Создаёт новое исключение без сообщения.
     */
    public InvalidQueryDataException() {
    }
}
