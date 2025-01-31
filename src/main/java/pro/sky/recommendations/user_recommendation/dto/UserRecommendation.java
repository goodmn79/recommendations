package pro.sky.recommendations.user_recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.recommendation.dto.RecommendationData;

import java.util.List;
import java.util.UUID;

/**
 * Объект передачи данных для списка рекомендаций клиента по его идентификатору.
 * <p>
 * Этот класс используется для представления данных о рекомендациях, которые предоставляются пользователю
 * на основе его уникального идентификатора (UUID).
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class UserRecommendation {

    /**
     * Уникальный идентификатор пользователя, для которого предоставлены рекомендации.
     */
    private UUID userId;

    /**
     * Список рекомендаций для пользователя.
     * Каждый элемент списка представляет собой отдельную рекомендацию банковского продукта.
     */
    private List<RecommendationData> recommendations;
}
