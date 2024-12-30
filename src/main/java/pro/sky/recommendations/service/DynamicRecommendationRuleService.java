package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.recommendations.dto.DynamicRecommendationRule;
import pro.sky.recommendations.mapper.castom_mapper.QueryMapper;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DynamicRecommendationRuleService {
    private final RecommendationService recommendationService;
    private final QueryService queryService;

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    public DynamicRecommendationRule create(DynamicRecommendationRule drr) {
        log.info("Invoke method 'DynamicRecommendationRule.create'");

        Recommendation recommendation = recommendationService.createRecommendation(drr);

        return dynamicRecommendationRuleBuilder(recommendation);
    }

    public DynamicRecommendationRule findById(UUID dynamicRecommendationRuleId) {
        log.info("Invoke method 'DynamicRecommendationRule.findById'");

        Recommendation recommendation = recommendationService.findById(dynamicRecommendationRuleId);

        return dynamicRecommendationRuleBuilder(recommendation);
    }

    public List<DynamicRecommendationRule> findAll() {
        log.info("Invoke method 'DynamicRecommendationRule.findAll'");

        List<Recommendation> recommendations = recommendationService.findAll();

        return recommendations
                .stream()
                .map(this::dynamicRecommendationRuleBuilder)
                .toList();
    }

    @Transactional
    public void deleteById(UUID dynamicRecommendationRuleId) {
        log.info("Invoke method 'DynamicRecommendationRule.deleteById'");

        recommendationService.deleteById(dynamicRecommendationRuleId);
    }

    private DynamicRecommendationRule dynamicRecommendationRuleBuilder(Recommendation recommendation) {
        log.info("Invoke method 'DynamicRecommendationRule.dynamicRecommendationRuleBuilder'");

        List<Query> queries = queryService.findAllByRecommendationId(recommendation.getId());

        return new DynamicRecommendationRule()
                .setId(recommendation.getId())
                .setProductName(recommendation.getProduct().getName())
                .setProductId(recommendation.getProduct().getId())
                .setProductText(recommendation.getProductText())
                .setRule(QueryMapper.toQueryData(queries));
    }
}
