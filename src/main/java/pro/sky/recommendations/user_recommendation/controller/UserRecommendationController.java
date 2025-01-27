package pro.sky.recommendations.user_recommendation.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendations.stats.service.StatsService;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;
import pro.sky.recommendations.user_recommendation.service.UserRecommendationService;

import java.util.UUID;

/**
 * Контроллер для обработки входящих HTTP-запросов, связанных с рекомендациями банковских продуктов.
 * <p>
 * Этот контроллер предоставляет API для получения рекомендаций для клиента по его идентификатору (UUID),
 * а также обеспечивает обновление статистики по каждому запросу.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */


@RestController
@RequestMapping("recommendation")
@RequiredArgsConstructor
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationController.class);

    private final StatsService statsService;

    /**
     * Обрабатывает GET-запрос для получения рекомендаций для клиента по его идентификатору.
     * <p>
     * Метод возвращает список рекомендаций для пользователя с указанным идентификатором,
     * а также обновляет статистику по запросу.
     * </p>
     *
     * @param userId Идентификатор пользователя (UUID), для которого необходимо получить рекомендации
     * @return Объект {@link UserRecommendation} с рекомендациями для данного пользователя
     */
    @GetMapping("{user_id}")
    public UserRecommendation userRecommendations(@PathVariable("user_id") UUID userId) {
        log.info("Вызван метод #getUserRecommendations.");

        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendations(userId);

        statsService.statsAccumulator(userRecommendation);

        return userRecommendation;
    }
}
