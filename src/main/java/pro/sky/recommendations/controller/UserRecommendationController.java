/*
Контроллер для обработки входящих HTTP-запросов и возвращения ответа
Обеспечивает передачу данных для получения рекомендаций банковских продуктов клиента по его идентификатору
Powered by ©AYE.team
 */

package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.service.UserRecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("recommendation")
@RequiredArgsConstructor
public class UserRecommendationController {
    private final UserRecommendationService userRecommendationService;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationController.class);

    @GetMapping("{user_id}")
    public UserRecommendation userRecommendation(@PathVariable("user_id") UUID userId) {
        log.info("Invoke method UserRecommendationController: 'getUserRecommendation'");
        return userRecommendationService.getUserRecommendations(userId);
    }
}
