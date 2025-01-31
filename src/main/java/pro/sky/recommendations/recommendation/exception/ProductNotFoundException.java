package pro.sky.recommendations.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается при отсутствии продукта в базе данных.
 * <p>
 * Этот класс расширяет {@link RuntimeException} и автоматически вызывает ошибку с кодом статуса 404 (NOT_FOUND) при возникновении исключения.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    /**
     * Конструктор исключения {@link ProductNotFoundException}.
     * <br>Создаёт новое исключение без сообщения.
     */
    public ProductNotFoundException() {
    }
}
