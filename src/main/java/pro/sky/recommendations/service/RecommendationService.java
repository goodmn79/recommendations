package pro.sky.recommendations.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.UserRecommendationSet;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.repository.UserRepository;
import pro.sky.recommendations.utility.Invest500RuleSet;
import pro.sky.recommendations.utility.SimpleCreditRuleSet;
import pro.sky.recommendations.utility.TopSavingRuleSet;

import java.util.UUID;

@Service
public class RecommendationService {
    private final UserRepository userRepository;
    private final Invest500RuleSet invest500;
    private final SimpleCreditRuleSet simpleCredit;
    private final TopSavingRuleSet topSaving;

    public RecommendationService(UserRepository userRepository,
                                 @Qualifier ("invest500") Invest500RuleSet invest500,
                                 @Qualifier ("simpleCredit")SimpleCreditRuleSet simpleCredit,
                                 @Qualifier ("topSaving")TopSavingRuleSet topSaving) {
        this.userRepository = userRepository;
        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
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

