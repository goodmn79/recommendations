/*
 * Исключение, которое выбрасывается, когда пользователь не найден.
 * Этот класс расширяет {@link RuntimeException} и автоматически вызывает ошибку
 * с кодом статуса 404 (NOT_FOUND) при возникновении исключения.
 * @author Powered by ©AYE.team
 * @version 1.0
 */

package pro.sky.recommendations.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения {@link UserNotFoundException}.
     * Создаёт новое исключение без сообщения.
     */
    public UserNotFoundException() {
    }
}
