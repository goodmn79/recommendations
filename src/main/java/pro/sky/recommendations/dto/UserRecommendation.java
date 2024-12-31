package pro.sky.recommendations.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pro.sky.recommendations.model.Recommendation;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserRecommendation {
    private UUID userId;
    private List<RecommendationDTO> recommendations;
}
