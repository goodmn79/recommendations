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
    private final StatsService statsService;

    private final TransactionTemplate transactionTemplate;

    private final QueryMapper queryMapper;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleManager.class);

    // Создание рекомендации банковского продукта
    public DynamicRecommendationRule saveRecommendation(DynamicRecommendationRule drr) {
        log.info("Сохранение динамического правила рекомендации...");

        Recommendation recommendation = this.createRecommendation(drr);
        transactionTemplate.execute(new TransactionSaveCallback(recommendation));

        DynamicRecommendationRule savedDrr = this.dynamicRecommendationRuleBuilder(recommendation);
        log.info("Динамическое правило рекомендации успешно сохранено.");
        return savedDrr;
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public DynamicRecommendationRule getById(UUID recommendationId) {
        log.info("Получение динамических правил рекомендаций по идентификатору...");

        Recommendation recommendation = recommendationService.findById(recommendationId);

        DynamicRecommendationRule drr = dynamicRecommendationRuleBuilder(recommendation);
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

        List<DynamicRecommendationRule> drrList = recommendations.stream().map(this::dynamicRecommendationRuleBuilder).toList();
        log.info("Динамические правила рекомендаций успешно получены.");
        return drrList;
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID recommendationId) {
        log.info("Удаление динамического правила рекомендации...");

        Recommendation recommendation = recommendationService.findById(recommendationId);

        transactionTemplate.execute(new TransactionDeleteCallback(recommendation));

        log.info("Динамическое правило рекомендации успешно удалено.");
    }

    private Recommendation createRecommendation(DynamicRecommendationRule drr) {

        Product product = productService.findById(drr.getProductId());

        log.info("Создание рекомендации...");
        Recommendation recommendation = new Recommendation().setId(UUID.randomUUID()).setProduct(product).setProductText(drr.getProductText());

        log.info("Создание правила...");
        List<Query> rule = queryMapper.toQuery(drr.getRule(), recommendation);

        log.info("Правило успешно создано.");
        recommendation.setRule(rule);

        log.info("Рекомендация успешно создана.");
        return recommendation;
    }

    // Создание динамического правила рекомендации банковского продукта
    private DynamicRecommendationRule dynamicRecommendationRuleBuilder(Recommendation recommendation) {
        log.info("Создание динамического правила рекомендации...");

        DynamicRecommendationRule drr = new DynamicRecommendationRule().setId(recommendation.getId()).setProductName(recommendation.getProduct().getName()).setProductId(recommendation.getProduct().getId()).setProductText(recommendation.getProductText()).setRule(queryMapper.toQueryData(recommendation.getRule()));

        log.info("Динамическое правило рекомендации успешно создано.");
        return drr;
    }

    private class TransactionSaveCallback extends TransactionCallbackWithoutResult {
        private final Recommendation recommendation;

        public TransactionSaveCallback(Recommendation recommendation) {
            this.recommendation = recommendation;
        }

        @Override
        public void doInTransactionWithoutResult(TransactionStatus status) {
            log.info("Процесс выполнения транзакции сохранения...");

            try {
                recommendationService.saveRecommendation(recommendation);
                log.info("Рекомендация успешно сохранена.");

                queryService.saveRule(recommendation);
                log.info("Правило рекомендации успешно сохранено.");

                statsService.createCounter(recommendation);
                log.info("Счётчик запросов успешно сохранён.");

            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("Не удалось сохранить динамическое правило рекомендации . Откат операции.", e);
                throw new TransactionExecuteException();
            }
            log.info("Процесс сохранения успешно завершён.");
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
                log.info("Рекомендация успешно удалена.");

                queryService.deleteBYRecommendationId(recommendation.getId());
                log.info("Правило рекомендации успешно удалено.");

                statsService.deleteCounter(recommendation);
                log.info("Счётчик запросов успешно удалён.");
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("Не удалось удалить динамическое правило рекомендации . Откат операции.", e);
                throw new TransactionExecuteException();
            }
        }
    }
}
