/*
Объект передачи данных для списка рекомендаций клиента по его идентификатору
Powered by ©AYE.team
 */

package pro.sky.recommendations.user_recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.recommendation.dto.RecommendationData;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserRecommendation {
    private UUID userId;
    private List<RecommendationData> recommendations;
}
