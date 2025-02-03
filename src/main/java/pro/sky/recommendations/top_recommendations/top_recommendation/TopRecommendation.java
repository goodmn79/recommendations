package pro.sky.recommendations.top_recommendations.top_recommendation;

import java.util.UUID;

/**
 * Интерфейс, который определяет структуру топовой рекомендации для пользователя.
 *<p>
 * Каждый класс, реализующий этот интерфейс, должен предоставлять информацию о продукте и SQL-запрос для проверки соответствия пользователя рекомендации.
 *</p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
public interface TopRecommendation {
    UUID getId();

    String getProductName();

    String getProductText();

    String getQuery();
}
