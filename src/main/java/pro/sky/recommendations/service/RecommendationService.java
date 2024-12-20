package pro.sky.recommendations.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationService(UserRepository userRepository,
                                 @Qualifier("invest500") Invest500RuleSet invest500,
                                 @Qualifier("simpleCredit") SimpleCreditRuleSet simpleCredit,
                                 @Qualifier("topSaving") TopSavingRuleSet topSaving) {
        this.userRepository = userRepository;
        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
    }

    public UserRecommendationSet checkRecommendation(UUID userId) {
        logger.info("Invoke method checkRecommendation");
        validateUserId(userId);
        UserRecommendationSet userRecommendationSet = new UserRecommendationSet(userId);
        invest500.validateRecommendationRule(userId).ifPresent(recommendation -> {
            logger.debug("Invest500 recommendation: {}", recommendation);
            userRecommendationSet.addRecommendation(recommendation);
        });
        simpleCredit.validateRecommendationRule(userId).ifPresent(recommendation -> {
            logger.debug("SimpleCredit recommendation: {}", recommendation);
            userRecommendationSet.addRecommendation(recommendation);
        });
        topSaving.validateRecommendationRule(userId).ifPresent(recommendation -> {
            logger.debug("TopSaving recommendation: {}", recommendation);
            userRecommendationSet.addRecommendation(recommendation);
        });
        return userRecommendationSet;
    }

    private void validateUserId(UUID userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}

