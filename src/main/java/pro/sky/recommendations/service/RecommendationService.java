package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.recommendations.dto.DynamicRecommendationRule;
import pro.sky.recommendations.dto.QueryData;
import pro.sky.recommendations.exception.DynamicRuleNotFoundException;
import pro.sky.recommendations.exception.RecommendationNotFoundException;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.repository.RecommendationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    private final ProductService productService;
    private final QueryService queryService;

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    public Recommendation createRecommendation(DynamicRecommendationRule drr) {
        log.info("Invoke method 'RecommendationService: createRecommendation'");

        drr.getRule().forEach(QueryData::validate);

        Product product = productService.findById(drr.getProductId());

        Recommendation recommendation = recommendationRepository.save(new Recommendation()
                .setProduct(product)
                .setProductText(drr.getProductText()));


        List<Query> rule = queryService.createRule(drr, recommendation);

        return recommendation.setRule(rule);
    }

    public Recommendation findById(UUID recommendationId) {
        log.info("Invoke method 'RecommendationService: findById'");

        return recommendationRepository.findById(recommendationId)
                .orElseThrow(DynamicRuleNotFoundException::new);
    }

    public List<Recommendation> findAll() {
        log.info("Invoke method 'RecommendationService: findAll'");

        List<Recommendation> recommendations = recommendationRepository.findAll();
        if (recommendations.isEmpty()) {
            throw new RecommendationNotFoundException();
        }
        return recommendations;
    }

    @Transactional
    public void deleteById(UUID recommendationId) {
        log.info("Invoke method 'RecommendationService: deleteById'");

        queryService.deleteBYRecommendationId(recommendationId);

        recommendationRepository.deleteById(recommendationId);
    }
}
