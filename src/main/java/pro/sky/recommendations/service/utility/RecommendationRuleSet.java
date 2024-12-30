package pro.sky.recommendations.service.utility;

import pro.sky.recommendations.model.Recommend;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<Recommend> validateRecommendationRule(UUID userId);
}
