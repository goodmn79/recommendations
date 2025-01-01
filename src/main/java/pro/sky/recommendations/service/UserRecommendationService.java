package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.constant.QueryType;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.mapper.castom_mapper.RecommendationMapper;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.repository.RecommendationRepository;
import pro.sky.recommendations.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {
    private final TransactionService transactionService;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationService.class);

    public UserRecommendation getUserRecommendations(UUID userId) {
        log.info("Invoke method 'UserRecommendationService: getUserRecommendations'");

        validateUserId(userId);

        List<Recommendation> recommendations = recommendationRepository.findAll();

        List<Recommendation> userRecommendations = recommendations
                .stream()
                .filter(r -> {
                    List<Query> rule = r.getRule();
                    return isComplianceRule(userId, rule);
                })
                .toList();

        return new UserRecommendation()
                .setUserId(userId)
                .setRecommendations(RecommendationMapper.fromRecommendationList(userRecommendations));
    }

    private boolean isComplianceRule(UUID userId, List<Query> rule) {

        for (Query query : rule) {
            if (!isCompliance(userId, query)) return false;
        }
        return true;
    }

    private boolean isCompliance(UUID userId, Query query) {
        String querySQL = queryGenerator(query);

        boolean isCompliance = transactionService.isCompliance(querySQL, userId);

        return checkNegate(isCompliance, query.getNegate());
    }

    private String queryGenerator(Query query) {

        String queryPattern = QueryType.QUERY_TYPES.get(query.getQuery());
        String[] arguments = query.getArguments();
        for (String argument : arguments) {
            queryPattern = StringUtils.replaceOnce(queryPattern, "-?-", argument);
        }
        return queryPattern;
    }

    private boolean checkNegate(boolean isCompliance, boolean negate) {
        if (isCompliance && negate) return false;
        if (!isCompliance && negate) return true;
        return isCompliance;
    }

    private void validateUserId(UUID userId) {

        if (userRepository.userIsExists(userId)) {
            log.info("User id validation: successfully");
            return;
        }
        throw new UserNotFoundException();
    }
}
