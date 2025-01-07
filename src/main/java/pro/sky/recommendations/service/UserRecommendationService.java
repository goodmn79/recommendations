/*
Файл сервиса для получения рекомендаций банковских продуктов доступных пользователю
Powered by ©AYE.team
 */

package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.enums.QueryType;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.mapper.castom_mapper.RecommendationMapper;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.model.Recommendation;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {
    private final RecommendationService recommendationService;
    private final TransactionService transactionService;

    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationService.class);

    // Получение всех рекомендаций банковских продуктов доступных пользователю по его идентификатору
    public UserRecommendation getUserRecommendations(UUID userId) {
        validateUserId(userId);

        log.info("Fetching user recommendations...");

        List<Recommendation> userRecommendations =
                recommendationService.findAll()
                        .stream()
                        .filter(r -> {
                            List<Query> rule = r.getRule();
                            return isComplianceRule(userId, rule);
                        })
                        .toList();

        if (userRecommendations.isEmpty()) {
            log.warn("No user recommendations found");
        } else {
            log.info("User recommendations successfully found");
        }

        return new UserRecommendation()
                .setUserId(userId)
                .setRecommendations(RecommendationMapper.fromRecommendationList(userRecommendations));
    }

    // Проверка соответствованя всех требований для правила рекомендации банковского продукта
    private boolean isComplianceRule(UUID userId, List<Query> rule) {
        log.info("Rule compliance check...");

        for (Query query : rule) {
            if (!isCompliance(userId, query)) {
                log.warn("Rule compliance check failed");
                return false;
            }
        }
        log.info("Check completed successfully");
        return true;
    }

    // Проверка соответствованя требования для правила рекомендации банковского продукта
    private boolean isCompliance(UUID userId, Query query) {
        log.debug("Checking compliance for user {}", userId);
        String querySQL = queryGenerator(query);

        boolean isCompliance = transactionService.isCompliance(querySQL, userId);

        log.debug("Checking complete with result - '{}'", isCompliance);
        return checkNegate(isCompliance, query.getNegate());
    }

    // Генерация SQL-запроса
    private String queryGenerator(Query query) {
        String queryType = query.getQuery();
        String queryPattern = QueryType.getQueryPattern(queryType);

        String[] arguments = query.getArguments();

        for (String argument : arguments) {
            queryPattern = StringUtils.replaceOnce(queryPattern, "-?-", argument);
        }
        return queryPattern;
    }

    // Проверка идентификатора отрицания для SQL-запроса
    private boolean checkNegate(boolean isCompliance, boolean negate) {
        if (isCompliance && negate) return false;
        if (!isCompliance && negate) return true;
        return isCompliance;
    }

    // Валидация пользователя по его идентификатору
    private void validateUserId(UUID userId) {
        if (!userService.userExists(userId)) {
            log.error("User does not exist");
            throw new UserNotFoundException();
        }
    }
}
