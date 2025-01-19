/*
Файл сервиса для создания, сохранения, получения и удаления рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pro.sky.recommendations.recommendation.dto.DynamicRecommendationRule;
import pro.sky.recommendations.recommendation.mapper.castom_mapper.QueryMapper;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.exception.RecommendationNotFoundException;
import pro.sky.recommendations.recommendation.exception.TransactionExecuteException;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.stats.service.StatsService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DynamicRecommendationRuleManager {
    private final RecommendationService recommendationService;
    private final QueryService queryService;
    private final ProductService productService;

    private final TransactionTemplate transactionTemplate;

    private final QueryMapper queryMapper;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleManager.class);
    private final StatsService statsService;

    // Создание рекомендации банковского продукта
    public DynamicRecommendationRule saveRecommendation(DynamicRecommendationRule drr) {
        log.info("Saving dynamic recommendation rule...");

        Recommendation recommendation = this.createRecommendation(drr);
        transactionTemplate.execute(new TransactionSaveCallback(recommendation));

        DynamicRecommendationRule savedDrr = this.dynamicRecommendationRuleBuilder(recommendation);
        log.info("Dynamic recommendation rule successfully saved");
        return savedDrr;
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public DynamicRecommendationRule getById(UUID recommendationId) {
        log.info("Fetching dynamic recommendation rule...");

        Recommendation recommendation = recommendationService.findById(recommendationId);

        DynamicRecommendationRule drr = dynamicRecommendationRuleBuilder(recommendation);
        log.info("Dynamic recommendation rule successfully fetched");
        return drr;
    }

    // Получение всех рекомендаций банковских продуктов
    public List<DynamicRecommendationRule> getAll() {
        log.info("Fetching all dynamic recommendation rules...");

        List<Recommendation> recommendations = recommendationService.findAll();
        if (recommendations.isEmpty()) {
            log.error("Dynamic recommendation rules not found");
            throw new RecommendationNotFoundException();
        }

        List<DynamicRecommendationRule> drrList = recommendations.stream().map(this::dynamicRecommendationRuleBuilder).toList();
        log.info("Dynamic recommendation rules successfully fetched");
        return drrList;
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID recommendationId) {
        log.info("Deleting dynamic recommendation rule...");

        Recommendation recommendation = recommendationService.findById(recommendationId);

        transactionTemplate.execute(new TransactionDeleteCallback(recommendation));

        log.info("Dynamic recommendation rule successfully deleted");
    }

    private Recommendation createRecommendation(DynamicRecommendationRule drr) {

        Product product = productService.findById(drr.getProductId());

        log.info("Creating recommendation...");
        Recommendation recommendation = new Recommendation().setId(UUID.randomUUID()).setProduct(product).setProductText(drr.getProductText());

        log.info("Creating rule...");
        List<Query> rule = queryMapper.toQuery(drr.getRule(), recommendation);

        log.info("Rule successfully created");
        recommendation.setRule(rule);

        log.info("Recommendation successfully created");
        return recommendation;
    }

    // Создание динамического правила рекомендации банковского продукта
    private DynamicRecommendationRule dynamicRecommendationRuleBuilder(Recommendation recommendation) {
        log.info("Building dynamic recommendation rule...");

        DynamicRecommendationRule drr = new DynamicRecommendationRule().setId(recommendation.getId()).setProductName(recommendation.getProduct().getName()).setProductId(recommendation.getProduct().getId()).setProductText(recommendation.getProductText()).setRule(queryMapper.toQueryData(recommendation.getRule()));

        log.info("Dynamic recommendation rule successfully built");
        return drr;
    }

    private class TransactionSaveCallback extends TransactionCallbackWithoutResult {
        private final Recommendation recommendation;

        public TransactionSaveCallback(Recommendation recommendation) {
            this.recommendation = recommendation;
        }

        @Override
        public void doInTransactionWithoutResult(TransactionStatus status) {
            try {
                recommendationService.saveRecommendation(recommendation);
                log.info("Recommendation was successfully saved");

                queryService.saveRule(recommendation);
                log.info("Rule was successfully saved");

                statsService.createCounter(recommendation);
                log.info("Counter was successfully created");
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("Dynamic recommendation rule saving failed. Rollback operation", e);
                throw new TransactionExecuteException();
            }
        }
    }

    private class TransactionDeleteCallback extends TransactionCallbackWithoutResult {
        private final Recommendation recommendation;

        public TransactionDeleteCallback(Recommendation recommendation) {
            this.recommendation = recommendation;
        }

        @Override
        public void doInTransactionWithoutResult(TransactionStatus status) {
            try {
                recommendationService.deleteById(recommendation.getId());
                log.info("Recommendation was successfully deleted");

                queryService.deleteBYRecommendationId(recommendation.getId());
                log.info("Rule was successfully deleted");

                statsService.deleteCounter(recommendation);
                log.info("Counter was successfully deleted");
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("Dynamic recommendation rule deletion failed. Rollback operation", e);
                throw new TransactionExecuteException();
            }
        }
    }
}