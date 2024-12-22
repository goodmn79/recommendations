package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.service.RecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("{userId}")
    public UserRecommendationSet getRecommendations(@PathVariable UUID userId) {
        return recommendationService.checkRecommendation(userId);
    }
}
