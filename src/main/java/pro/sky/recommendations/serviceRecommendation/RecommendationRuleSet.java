package pro.sky.recommendations.serviceRecommendation;

import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface RecommendationRuleSet {
    Optional <Optional<Recommendation>> validateRecommendationRule(List<Transaction> transactions);
}
