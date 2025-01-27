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

/*
 * Сервис для работы с рекомендациями банковских продуктов.
 * Этот класс предоставляет методы для создания, сохранения, получения и удаления рекомендаций.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    /**
     * Сохранение рекомендации банковского продукта.
     * Метод сохраняет рекомендацию в базе данных.
     *
     * @param recommendation объект {@link Recommendation}, который нужно сохранить.
     */
    public void saveRecommendation(Recommendation recommendation) {
        log.info("Сохранение рекомендации...");

        recommendationRepository.save(recommendation);
    }

    /**
     * Получение рекомендации банковского продукта по её идентификатору.
     * Если рекомендация не найдена, выбрасывается исключение {@link RecommendationNotFoundException}.
     *
     * @param recommendationId идентификатор рекомендации.
     * @return объект {@link Recommendation}, соответствующий указанному идентификатору.
     * @throws RecommendationNotFoundException если рекомендация с данным идентификатором не найдена.
     */
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

    /**
     * Получение всех рекомендаций банковских продуктов.
     * Если рекомендации не найдены, возвращается пустой список.
     *
     * @return список объектов {@link Recommendation}, содержащий все рекомендации.
     */
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

    /**
     * Удаление рекомендации банковского продукта по её идентификатору.
     * Сначала проверяется, существует ли рекомендация с данным идентификатором.
     *
     * @param recommendationId идентификатор рекомендации для удаления.
     */
    public void deleteById(UUID recommendationId) {
        log.info("Удаление рекомендации по идентификатору...");

        // Проверка существования рекомендации
        this.findById(recommendationId);

        recommendationRepository.deleteById(recommendationId);
    }
}
