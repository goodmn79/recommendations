/*
Объект передачи данных для рекомендации банковского продукта
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class RecommendationData {
    private UUID id;
    private String productName;
    private String productText;
}
