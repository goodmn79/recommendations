package pro.sky.recommendations.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * DTO класс для передачи данных о рекомендации банковского продукта.
 * Содержит основную информацию о рекомендуемом продукте.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@Data
@Accessors(chain = true)
public class RecommendationData {

    private UUID id;

    private String productName;

    private String productText;
}
