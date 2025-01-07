/*
Файл сервиса для создания, сохранения, получения и удаления правил рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.exception.RecommendationRuleNotExistException;
import pro.sky.recommendations.exception.SaveDataException;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.repository.QueryRepository;

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
        List<Query> savedRule = this.saveRule(rule);
        if (savedRule.isEmpty()) {
            log.error("Rule could not be saved.");
            throw new SaveDataException();
        }
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
    private List<Query> saveRule(List<Query> queries) {
        log.debug("Invoke method: 'saveRule'");

        return queryRepository.saveAll(queries);
    }
}
