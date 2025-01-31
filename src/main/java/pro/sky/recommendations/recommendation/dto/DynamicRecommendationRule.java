package pro.sky.recommendations.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

/**
 * DTO класс для передачи данных о динамическом правиле рекомендации банковских продуктов.
 * <p>
 * Содержит информацию о продукте и правилах его рекомендации.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class DynamicRecommendationRule {
    /**
     * Уникальный идентификатор правила рекомендации
     */
    private UUID id;

    /**
     * Название банковского продукта
     */
    private String productName;

    /**
     * Уникальный идентификатор продукта
     */
    private UUID productId;

    /**
     * Текстовое описание продукта
     */
    private String productText;

    /**
     * Список правил запросов для определения рекомендации
     */
    private List<QueryData> rule;
}
