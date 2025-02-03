package pro.sky.recommendations.top_recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.dto.RecommendationData;
import pro.sky.recommendations.recommendation.repository.TransactionRepository;
import pro.sky.recommendations.recommendation.service.RecommendationService;
import pro.sky.recommendations.top_recommendations.top_recommendation.TopRecommendation;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для формирования топовых рекомендаций для конкретного пользователя.
 *<p>
 * Сервис предоставляет метод для проверки на соответствие пользователя требованиям для каждой рекомендации.
 *</p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */

@Service
@RequiredArgsConstructor
public class TopRecommendationService {

    private final TransactionRepository transactionRepository;

    private final List<TopRecommendation> topRecommendations;

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    /**
     * Получение списка топовых рекомендаций для заданного пользователя. 
     *<br> Этот метод использует {@link TransactionRepository#isCompliance(Query, Long)} проверяя, соответствует ли пользователь каждой из рекомендаций.
     *
     * @param userId уникальный идентификатор пользователя, для которого генерируются рекомендации.
     * @return список объектов {@link RecommendationData}, представляющих топовые рекомендации для пользователя.
     */
    public List<RecommendationData> getTopRecommendationsForUser(UUID userId) {
        log.info("Формирование списка топовых рекомендаций...");

        List<RecommendationData> recommendationDataList = topRecommendations
                .stream()
                .filter(tr -> {
                    String query = tr.getQuery();
                    boolean compliance = transactionRepository.isCompliance(query, userId);
                    log.debug("Проверка запроса пройдена с результатом: '{}'", compliance);
                    return compliance;
                })
                .map(tr -> new RecommendationData()
                        .setId(tr.getId())
                        .setProductName(tr.getProductName())
                        .setProductText(tr.getProductText()))
                .toList();
        log.info("Список топовых рекомендаций успешно сформирован.");
        return recommendationDataList;
    }
}
