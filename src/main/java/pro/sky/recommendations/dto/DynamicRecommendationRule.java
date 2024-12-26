package pro.sky.recommendations.dto;

import lombok.Data;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;

@Data
public class DynamicRecommendationRule {
    private Recommendation recommendation;
    private Query[] queries;
}
