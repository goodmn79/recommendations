/*
Объект передачи данных для динамического правила рекомендации
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class DynamicRecommendationRule {
    private UUID id;
    private String productName;
    private UUID productId;
    private String productText;
    private List<QueryData> rule;
}
