/*
Файл сервиса для создания, сохранения, получения и удаления правил рекомендации банковских продуктов
Powered by ©AYE.team
 */

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

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;

    private final Logger log = LoggerFactory.getLogger(QueryService.class);

    // Создание правила рекомендации банковского продукта
    public void saveRule(Recommendation recommendation) {
        log.info("Saving rule...");

        List<Query> rule = recommendation.getRule();
        this.saveRule(rule);
    }

    // Получение правила по идентификатору рекомендации банковского продукта
    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.info("Fetching all rules by recommendation id...");

        List<Query> foundQueries = queryRepository.findAllByRecommendationId(recommendationId);
        if (foundQueries.isEmpty()) {
            log.error("Recommendation rule not found");
            throw new RecommendationRuleNotExistException();
        }
        log.info("Recommendation rule successfully found");
        return foundQueries;
    }

    // Удаление правила по идентификатору рекомендации банковского продукта
    public void deleteBYRecommendationId(UUID recommendationId) {
        log.info("Deleting rule by recommendation id...");

        queryRepository.deleteAllByRecommendationId(recommendationId);
    }

    // Сохранение правила рекомендации банковского продукта
    private void saveRule(List<Query> queries) {
        log.debug("Invoke method: 'saveRule'");

        queryRepository.saveAll(queries);
    }
}
