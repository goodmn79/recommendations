/*
Файл сервиса для создания, сохранения, получения и удаления рекомендации банковских продуктов
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.exception.RecommendationNotFoundException;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.repository.RecommendationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    // Сохранение рекомендации банковского продукта в базе данных
    public void saveRecommendation(Recommendation recommendation) {
        log.info("Saving recommendation...");

        recommendationRepository.save(recommendation);
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public Recommendation findById(UUID recommendationId) {
        log.info("Fetching recommendation...");

        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> {
                    log.error("Recommendation not found");
                    return new RecommendationNotFoundException();
                });
        log.info("Recommendation successfully fetched");
        return recommendation;
    }

    // Получение всех рекомендаций банковских продуктов
    public List<Recommendation> findAll() {
        log.info("Fetching recommendations...");

        List<Recommendation> recommendations = recommendationRepository.findAll();
        if (recommendations.isEmpty()) {
            log.warn("Recommendations not found");
        } else {
            log.info("Recommendations successfully fetched");
        }
        return recommendations;
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID recommendationId) {
        log.info("Deleting recommendation by id...");

        recommendationRepository.deleteById(recommendationId);
    }
}
