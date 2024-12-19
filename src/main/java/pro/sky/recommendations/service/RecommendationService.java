package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.utility.InvestRule;
import pro.sky.recommendations.utility.SimpleCreditRule;
import pro.sky.recommendations.utility.TopSavingRule;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    private final InvestRule investRule;
    private final SimpleCreditRule simpleCreditRule;
    private final TopSavingRule topSavingRule;

    // Проверяем подходят ли продукты (invest 500, простой кредит или Top Saving) для рекомендаций пользователю
    public Recommendation checkRecommendation(UUID userId) {
        Recommendation recommendation = new Recommendation();
        investRule.validateRecommendationRule(userId);
        simpleCreditRule.validateRecommendationRule(userId);
        topSavingRule.validateRecommendationRule(userId);
        return recommendation;
    }

    public UserRecommendationSet getUserRecommendationSet(UUID userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserRecommendationSet userRecommendationSet = new UserRecommendationSet(userId);
        /* Перед тем, как положить продукты для рекомендации в наш Set<Recommendation> recommendations
        в объект UserRecommendationSet делаем проверку с помощью метода checkRecommendation(userId)
         */
        userRecommendationSet.addRecommendation(checkRecommendation(userId));
        return userRecommendationSet;
    }


}

