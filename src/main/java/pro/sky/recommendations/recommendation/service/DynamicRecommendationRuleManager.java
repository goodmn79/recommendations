/*
Файл сервиса для создания, сохранения, получения и удаления рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.recommendations.recommendation.dto.DynamicRecommendationRule;
import pro.sky.recommendations.recommendation.exception.RecommendationNotFoundException;
import pro.sky.recommendations.recommendation.exception.TransactionExecuteException;
import pro.sky.recommendations.recommendation.mapper.castom_mapper.QueryMapper;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.stats.service.StatsService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DynamicRecommendationRuleManager {
    private final RecommendationService recommendationService;
    private final QueryService queryService;
    private final ProductService productService;
    private final StatsService statsService;

    private final QueryMapper queryMapper;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleManager.class);

    // Создание рекомендации банковского продукта
    @Transactional
    public DynamicRecommendationRule saveRecommendation(DynamicRecommendationRule drr) {
        log.info("Сохранение динамического правила рекомендации...");

        Recommendation recommendation = this.createRecommendation(drr);

        try {
            recommendationService.saveRecommendation(recommendation);

            queryService.saveRule(recommendation);

            statsService.createCounter(recommendation);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TransactionExecuteException();
        }

        DynamicRecommendationRule savedDrr = this.build(recommendation);
        log.info("Динамическое правило рекомендации успешно сохранено.");
        return savedDrr;
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public DynamicRecommendationRule getById(UUID recommendationId) {
        log.info("Получение динамических правил рекомендаций по идентификатору...");

        Recommendation recommendation = recommendationService.findById(recommendationId);

        DynamicRecommendationRule drr = build(recommendation);
        log.info("Динамическое правило рекомендации успешно получено.");
        return drr;
    }

    // Получение всех рекомендаций банковских продуктов
    public List<DynamicRecommendationRule> getAll() {
        log.info("Получение динамических правил рекомендаций...");

        List<Recommendation> recommendations = recommendationService.findAll();
        if (recommendations.isEmpty()) {
            log.error("Динамических правил рекомендации не найдено!");
            throw new RecommendationNotFoundException();
        }

        List<DynamicRecommendationRule> drrList = recommendations.stream().map(this::build).toList();
        log.info("Динамические правила рекомендаций успешно получены.");
        return drrList;
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    @Transactional
    public void deleteById(UUID recommendationId) {
        log.info("Удаление динамического правила рекомендации...");

        try {
            recommendationService.deleteById(recommendationId);

            queryService.deleteBYRecommendationId(recommendationId);

            statsService.deleteCounter(recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TransactionExecuteException();
        }
        log.info("Динамическое правило рекомендации успешно удалено.");
    }

    private Recommendation createRecommendation(DynamicRecommendationRule drr) {

        Product product = productService.findById(drr.getProductId());

        log.info("Создание рекомендации...");
        Recommendation recommendation =
                new Recommendation()
                        .setId(UUID.randomUUID())
                        .setProduct(product)
                        .setProductText(drr.getProductText());

        log.info("Создание правила...");
        List<Query> rule = queryMapper.toQuery(drr.getRule(), recommendation);

        log.info("Правило успешно создано.");
        recommendation.setRule(rule);

        log.info("Рекомендация успешно создана.");
        return recommendation;
    }

    // Создание динамического правила рекомендации банковского продукта
    private DynamicRecommendationRule build(Recommendation recommendation) {
        log.info("Создание динамического правила рекомендации...");

        DynamicRecommendationRule drr =
                new DynamicRecommendationRule()
                        .setId(recommendation.getId())
                        .setProductName(recommendation.getProduct().getName())
                        .setProductId(recommendation.getProduct().getId())
                        .setProductText(recommendation.getProductText())
                        .setRule(queryMapper.toQueryData(recommendation.getRule()));

        log.info("Динамическое правило рекомендации успешно создано.");
        return drr;
    }
}
