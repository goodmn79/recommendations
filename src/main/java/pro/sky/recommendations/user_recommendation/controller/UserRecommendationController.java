/*
Контроллер для обработки входящих HTTP-запросов и возвращения ответа
Обеспечивает передачу данных для получения рекомендаций банковских продуктов клиента по его идентификатору
Powered by ©AYE.team
 */

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

@RestController
@RequestMapping("recommendation")
@RequiredArgsConstructor
public class UserRecommendationController {
    private final UserRecommendationService userRecommendationService;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationController.class);
    private final StatsService statsService;

    @GetMapping("{user_id}")
    public UserRecommendation userRecommendations(@PathVariable("user_id") UUID userId) {
        log.info("Invoke method: 'getUserRecommendations'");

        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendations(userId);
        statsService.incrementor(userRecommendation);

        return userRecommendation;
    }
}
