package pro.sky.recommendations.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * DTO класс для передачи данных о рекомендации банковского продукта.
 * <p>
 * Содержит основную информацию о рекомендуемом продукте.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class RecommendationData {

    private UUID id;

    private String productName;

    private String productText;
}
