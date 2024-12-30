package pro.sky.recommendations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.service.RecommendService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendService recommendService;

    @GetMapping("{userId}")
    public UserRecommendationSet getRecommendations(@PathVariable UUID userId) {
        return recommendService.checkRecommendation(userId);
    }
}
