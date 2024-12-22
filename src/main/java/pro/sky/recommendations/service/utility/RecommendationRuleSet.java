package pro.sky.recommendations.service.utility;

import pro.sky.recommendations.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommendation> validateRecommendationRule(UUID userId);
}
