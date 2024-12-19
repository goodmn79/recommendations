package pro.sky.recommendations.utility;

import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional <Optional<Recommendation>> validateRecommendationRule(UUID userId);
}
