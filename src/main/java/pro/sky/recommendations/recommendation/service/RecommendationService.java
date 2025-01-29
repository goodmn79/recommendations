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
        log.info("Сохранение рекомендации...");

        recommendationRepository.save(recommendation);
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public Recommendation findById(UUID recommendationId) {
        log.info("Получение рекомендации...");

        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> {
                    log.error("Рекомендация не найдена!");
                    return new RecommendationNotFoundException();
                });
        log.info("Рекомендация успешно получена.");
        return recommendation;
    }

    // Получение всех рекомендаций банковских продуктов
    public List<Recommendation> findAll() {
        log.info("Получение рекомендаций...");

        List<Recommendation> recommendations = recommendationRepository.findAll();
        if (recommendations.isEmpty()) {
            log.warn("Рекомендаций не найдено!");
        } else {
            log.info("Рекомендации успешно получены.");
        }
        return recommendations;
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID recommendationId) {
        log.info("Удаление рекомендации по идентификатору...");

        this.findById(recommendationId);

        recommendationRepository.deleteById(recommendationId);
    }
}
