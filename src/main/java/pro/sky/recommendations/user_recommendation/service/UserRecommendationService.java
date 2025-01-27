package pro.sky.recommendations.user_recommendation.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.enums.QueryType;
import pro.sky.recommendations.recommendation.exception.UserNotFoundException;
import pro.sky.recommendations.recommendation.mapper.castom_mapper.RecommendationMapper;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.service.RecommendationService;
import pro.sky.recommendations.recommendation.service.TransactionService;
import pro.sky.recommendations.recommendation.service.UserService;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для получения рекомендаций банковских продуктов, доступных пользователю.
 * <p>
 * Этот сервис предоставляет методы для извлечения рекомендаций банковских продуктов на основе идентификатора пользователя.
 * Он также включает валидацию идентификатора пользователя и проверку соответствия различных правил рекомендаций.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class UserRecommendationService {
    private final RecommendationService recommendationService;
    private final TransactionService transactionService;
    private final UserService userService;

    private final RecommendationMapper recommendationMapper;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationService.class);

    /**
     * Получение всех рекомендаций банковских продуктов, доступных пользователю по его идентификатору.
     * Метод кэширует результаты на основе идентификатора пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Объект {@link UserRecommendation}, содержащий рекомендации для данного пользователя.
     */
    @Cacheable(value = "userRecommendationCache", key = "#userId")
    // Получение всех рекомендаций банковских продуктов доступных пользователю по его идентификатору
    public UserRecommendation getUserRecommendations(UUID userId) {
        validateUserId(userId);

        log.info("Получение рекомендаций для пользователя...");

        List<Recommendation> userRecommendations =
                recommendationService.findAll()
                        .stream()
                        .filter(r -> {
                            List<Query> rule = r.getRule();
                            return isComplianceRule(userId, rule);
                        })
                        .toList();

        if (userRecommendations.isEmpty()) log.warn("Рекомендаций для пользователя не найдено.");

        UserRecommendation userRecommendation = new UserRecommendation()
                .setUserId(userId)
                .setRecommendations(recommendationMapper.fromRecommendationList(userRecommendations));

        log.info("Рекомендации для пользователя успешно получены.");
        return userRecommendation;
    }

    /**
     * Проверка соответствия всех требований для правила рекомендации банковского продукта.
     *
     * @param userId Идентификатор пользователя.
     * @param rule   Список правил для рекомендации.
     * @return {@code true}, если все правила соблюдены, {@code false} в противном случае.
     */
    private boolean isComplianceRule(UUID userId, List<Query> rule) {
        log.warn("Проверка на соответствие правилу...");

        for (Query query : rule) {
            if (!isCompliance(userId, query)) {
                log.error("Проверка не пройдена!");
                return false;
            }
        }
        log.info("Проверка прошла успешно");
        return true;
    }

    /**
     * Проверка соответствия конкретному требованию для правила рекомендации банковского продукта.
     *
     * @param userId Идентификатор пользователя.
     * @param query  Объект {@link Query}, представляющий конкретное требование.
     * @return {@code true}, если правило соблюдается, {@code false} в противном случае.
     */
    private boolean isCompliance(UUID userId, Query query) {
        log.debug("Проверка соответствия правилу для пользователя с id = {}", userId);
        String querySQL = queryGenerator(query);

        boolean isCompliance = transactionService.isCompliance(querySQL, userId);

        log.debug("Проверка завершена с результатом - '{}'", isCompliance);
        return checkNegate(isCompliance, query.getNegate());
    }

    /**
     * Генерация SQL-запроса на основе данных из объекта {@link Query}.
     *
     * @param query Объект {@link Query}, который содержит данные для формирования SQL-запроса.
     * @return Сформированный SQL-запрос.
     */
    private String queryGenerator(Query query) {
        String queryType = query.getQuery();
        String queryPattern = QueryType.getQueryPattern(queryType);

        String[] arguments = query.getArguments();

        for (String argument : arguments) {
            queryPattern = StringUtils.replaceOnce(queryPattern, "-?-", argument);
        }
        return queryPattern;
    }

    /**
     * Проверка идентификатора отрицания для SQL-запроса.
     *
     * @param isCompliance Результат проверки на соответствие.
     * @param negate       Флаг отрицания для правила.
     * @return {@code true}, если условие соблюдено с учётом флага отрицания, {@code false} в противном случае.
     */
    private boolean checkNegate(boolean isCompliance, boolean negate) {
        if (isCompliance && negate) return false;
        if (!isCompliance && negate) return true;
        return isCompliance;
    }

    /**
     * Валидация пользователя по его идентификатору.
     *
     * @param userId Идентификатор пользователя.
     * @throws UserNotFoundException Если пользователь не найден.
     */
    private void validateUserId(UUID userId) {
        if (!userService.userExists(userId)) {
            log.error("Пользователь не существует");
            throw new UserNotFoundException();
        }
    }
}
