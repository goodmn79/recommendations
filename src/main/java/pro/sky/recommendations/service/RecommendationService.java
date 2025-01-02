/*
Файл сервиса для создания, сохранения, получения и удаления рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.recommendations.dto.DynamicRecommendationRule;
import pro.sky.recommendations.dto.QueryData;
import pro.sky.recommendations.exception.DynamicRecommendationRuleNotFoundException;
import pro.sky.recommendations.exception.RecommendationNotFoundException;
import pro.sky.recommendations.mapper.castom_mapper.QueryMapper;
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

    // Создание рекомендации банковского продукта
    public DynamicRecommendationRule createRecommendation(DynamicRecommendationRule drr) {
        log.info("Invoke method: 'createRecommendation'");

        List<QueryData> queryData = drr.getRule();
        queryData.forEach(QueryData::validate);

        Product product = productService.findById(drr.getProductId());

        Recommendation savedRecommendation = recommendationRepository.save(new Recommendation()
                .setProduct(product)
                .setProductText(drr.getProductText()));

        List<Query> savedRule = queryService.createRule(queryData, savedRecommendation);


        return new DynamicRecommendationRule()
                .setId(savedRecommendation.getId())
                .setProductName(product.getName())
                .setProductId(product.getId())
                .setProductText(savedRecommendation.getProductText())
                .setRule(QueryMapper.toQueryData(savedRule));
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public DynamicRecommendationRule findById(UUID recommendationId) {
        log.info("Invoke method: 'findById'");

        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(DynamicRecommendationRuleNotFoundException::new);

        return dynamicRecommendationRuleBuilder(recommendation);
    }

    // Получение всех рекомендаций банковских продуктов
    public List<DynamicRecommendationRule> findAll() {
        log.info("Invoke method: 'findAll'");

        List<Recommendation> recommendations = recommendationRepository.findAll();
        if (recommendations.isEmpty()) {
            throw new RecommendationNotFoundException();
        }

        return recommendations
                .stream()
                .map(this::dynamicRecommendationRuleBuilder)
                .toList();
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    @Transactional
    public void deleteById(UUID recommendationId) {
        log.info("Invoke method: 'deleteById'");

        queryService.deleteBYRecommendationId(recommendationId);

        recommendationRepository.deleteById(recommendationId);
    }

    // Создание динамического правила рекомендации банковского продукта
    private DynamicRecommendationRule dynamicRecommendationRuleBuilder(Recommendation recommendation) {
        log.info("Invoke method: 'dynamicRecommendationRuleBuilder'");

        return new DynamicRecommendationRule()
                .setId(recommendation.getId())
                .setProductName(recommendation.getProduct().getName())
                .setProductId(recommendation.getProduct().getId())
                .setProductText(recommendation.getProductText())
                .setRule(QueryMapper.toQueryData(recommendation.getRule()));
    }
}
