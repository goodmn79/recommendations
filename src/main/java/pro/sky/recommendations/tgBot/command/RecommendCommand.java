/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.dto.RecommendationData;
import pro.sky.recommendations.dto.UserRecommendation;
import pro.sky.recommendations.service.UserRecommendationService;
import pro.sky.recommendations.tgBot.service.BotService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RecommendCommand implements Command {
    public static final String RECOMMEND = "/recommend";

    private final CommandHandler commandHandler;
    private final UserRecommendationService userRecommendationService;

    private final Logger log = LoggerFactory.getLogger(RecommendCommand.class);

    /**
     * передаёт текст ответного сообщения
     *
     * @param text текст входящего сообщения
     * @return ответного сообщения
     */
    @Override
    public String respond(String text) {

        return recommend(text);
    }

    /**
     * формирует текст ответного сообщения
     * использует(@linc CommandHandler#extractFullName(text)) для извлечения имени и фамилии пользователя
     * использует(@linc CommandHandler#getUserId(fullName)) для получения идентификатора пользователя
     * использует(@linc UserRecommendation#getUserRecommendations(userId.get())) для получения доступных рекомендаций
     *
     * @param text текст входящего сообщения
     * @return сформированный текст ответного сообщения в зависимости от полученных параметров
     */
    private String recommend(String text) {
        log.info("Формирование ответа на команду '/recommend'...");

        String fullName = commandHandler.extractFullName(text);
        if (StringUtils.isBlank(fullName)) {
            log.warn("Введены некорректные данные. Ответ сформирован.");
            return BotService.INCORRECT_DATA;
        } else {
            log.info("Имя и фамилия пользователя успешно извлечены.");
        }

        Optional<UUID> userId = commandHandler.getUserId(fullName);
        if (userId.isEmpty()) {
            log.warn("Пользователь не найден! Ответ сформирован.");
            return "Пользователь " + fullName + " не найден";
        }

        String greetings = "Здравствуйте " + fullName + "!\n";

        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendations(userId.get());

        List<RecommendationData> userRecommendations = userRecommendation.getRecommendations();
        if (userRecommendations.isEmpty()) {
            log.warn("Рекомендаций не найдено! Ответ сформирован.");
            return greetings + "К сожалению, в настоящий момент для Вас нет подходящих продуктов";
        } else {
            log.info("Рекомендации для пользователя успешно получены. Ответ сформирован.");
            return greetings + "Новые продукты для Вас:\n" + commandHandler.recommendationsTextBuilder(userRecommendations);
        }
    }
}
