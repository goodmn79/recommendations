package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.exception.RecommendationRuleNotExistException;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.repository.QueryRepository;

import java.util.List;
import java.util.UUID;

/*
 * Сервис для работы с правилами рекомендаций банковских продуктов.
 * Этот класс предоставляет методы для сохранения, получения и удаления правил рекомендаций.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository queryRepository;

    private final Logger log = LoggerFactory.getLogger(QueryService.class);

    /**
     * Сохранение правила рекомендации для банковского продукта.
     * Метод сохраняет все запросы (правила) для указанной рекомендации.
     *
     * @param recommendation объект {@link Recommendation}, содержащий правила для сохранения.
     */
    public void saveRule(Recommendation recommendation) {
        log.info("Сохранение правила...");

        List<Query> rule = recommendation.getRule();
        queryRepository.saveAll(rule);
    }

    /**
     * Получение всех правил рекомендации для указанной рекомендации.
     * Если правила не найдены, выбрасывается исключение {@link RecommendationRuleNotExistException}.
     *
     * @param recommendationId идентификатор рекомендации.
     * @return список объектов {@link Query}, представляющих правила для рекомендации.
     * @throws RecommendationRuleNotExistException если правила для рекомендации не найдены.
     */
    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.info("Получение правила по идентификатору рекомендации...");

        List<Query> foundQueries = queryRepository.findAllByRecommendationId(recommendationId);
        if (foundQueries.isEmpty()) {
            log.error("Правило для рекомендации не найдено!");
            throw new RecommendationRuleNotExistException();
        }
        log.info("Правило для рекомендации успешно получено");
        return foundQueries;
    }

    /**
     * Удаление всех правил для указанной рекомендации.
     *
     * @param recommendationId идентификатор рекомендации, для которой нужно удалить правила.
     */
    public void deleteBYRecommendationId(UUID recommendationId) {
        log.info("Удаление правила по идентификатору рекомендации...");

        queryRepository.deleteAllByRecommendationId(recommendationId);
    }
}
