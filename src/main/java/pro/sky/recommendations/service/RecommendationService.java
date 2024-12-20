package pro.sky.recommendations.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.utility.RecommendationRuleSet;

import java.util.UUID;

@Service
public class RecommendationService {
    private final UserRepository userRepository;
    private final RecommendationRuleSet invest500;
    private final RecommendationRuleSet topSaving;
    private final RecommendationRuleSet simpleCredit;

    public RecommendationService(UserRepository userRepository,
                                 @Qualifier("topSaving") RecommendationRuleSet topSaving,
                                 @Qualifier("simpleCredit") RecommendationRuleSet simpleCredit,
                                 @Qualifier("invest500") RecommendationRuleSet invest500) {
        this.userRepository = userRepository;
        this.topSaving = topSaving;
        this.simpleCredit = simpleCredit;
        this.invest500 = invest500;
    }

    // Проверяем подходят ли продукты (invest 500, простой кредит или Top Saving) для рекомендаций пользователю
    public UserRecommendationSet checkRecommendation(UUID userId) {
        validateUserId(userId);
        UserRecommendationSet userRecommendationSet = new UserRecommendationSet(userId);
        invest500.validateRecommendationRule(userId).ifPresent(userRecommendationSet::addRecommendation);
        simpleCredit.validateRecommendationRule(userId).ifPresent(userRecommendationSet::addRecommendation);
        topSaving.validateRecommendationRule(userId).ifPresent(userRecommendationSet::addRecommendation);
        return userRecommendationSet;
    }

    private void validateUserId(UUID userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}

