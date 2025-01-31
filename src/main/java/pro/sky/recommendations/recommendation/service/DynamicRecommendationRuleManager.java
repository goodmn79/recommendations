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

/**
 * Сервис для работы с динамическими правилами рекомендаций банковских продуктов.
 * Этот класс предоставляет методы для создания, сохранения, получения и удаления рекомендаций.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DynamicRecommendationRuleManager {
    private final RecommendationService recommendationService;

    private final QueryService queryService;

    private final ProductService productService;

    private final StatsService statsService;

    private final QueryMapper queryMapper;

    private final Logger log = LoggerFactory.getLogger(DynamicRecommendationRuleManager.class);

    /**
     * Сохранение динамического правила рекомендации.
     * Этот метод создает рекомендацию, сохраняет её в базе данных, а также сохраняет правила и статистику.
     *
     * @param drr объект, содержащий данные для создания и сохранения динамического правила.
     * @return сохраненное динамическое правило рекомендации.
     */
    @Transactional
    public DynamicRecommendationRule saveRecommendation(DynamicRecommendationRule drr) {
        log.info("Сохранение динамического правила рекомендации...");

        // Создание рекомендации
        Recommendation recommendation = this.createRecommendation(drr);

        try {
            // Сохранение рекомендации, правил и статистики
            recommendationService.saveRecommendation(recommendation);
            queryService.saveRule(recommendation);
            statsService.createCounter(recommendation);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TransactionExecuteException();
        }

        // Построение и возврат сохраненного динамического правила рекомендации
        DynamicRecommendationRule savedDrr = this.build(recommendation);
        log.info("Динамическое правило рекомендации успешно сохранено.");
        return savedDrr;
    }

    /**
     * Получение динамического правила рекомендации по его идентификатору.
     *
     * @param recommendationId идентификатор рекомендации.
     * @return динамическое правило рекомендации.
     */
    public DynamicRecommendationRule getById(UUID recommendationId) {
        log.info("Получение динамических правил рекомендаций по идентификатору...");

        Recommendation recommendation = recommendationService.findById(recommendationId);

        DynamicRecommendationRule drr = build(recommendation);
        log.info("Динамическое правило рекомендации успешно получено.");
        return drr;
    }

    /**
     * Получение всех динамических правил рекомендаций.
     *
     * @return список всех динамических правил рекомендаций.
     */
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

    /**
     * Удаление динамического правила рекомендации по его идентификатору.
     * Этот метод удаляет рекомендацию, её правила и статистику.
     *
     * @param recommendationId идентификатор рекомендации, которую необходимо удалить.
     */
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

    /**
     * Создание рекомендации банковского продукта из данных динамического правила.
     *
     * @param drr объект, содержащий данные для создания рекомендации.
     * @return созданная рекомендация.
     */
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

    /**
     * Построение объекта динамического правила рекомендации на основе объекта Recommendation.
     *
     * @param recommendation объект Recommendation.
     * @return объект DynamicRecommendationRule, представляющий динамическое правило рекомендации.
     */
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
