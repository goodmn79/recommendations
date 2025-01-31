package pro.sky.recommendations.tg_bot.command;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.dto.RecommendationData;
import pro.sky.recommendations.recommendation.model.User;
import pro.sky.recommendations.recommendation.service.UserService;
import pro.sky.recommendations.tg_bot.service.BotService;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;
import pro.sky.recommendations.user_recommendation.service.UserRecommendationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Команда для получения рекомендаций для пользователя.
 * <p>
 * Этот класс обрабатывает команду, извлекая имя пользователя из текста сообщения, находя соответствующие рекомендации для пользователя и возвращая ответ в виде текстового сообщения с рекомендациями или информацией об их отсутствии.
 * <br>Реализует интерфейс {@link Command}.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Component
@RequiredArgsConstructor
public class Recommend implements Command {
    private final UserService userService;
    private final UserRecommendationService userRecommendationService;

    private final Logger log = LoggerFactory.getLogger(Recommend.class);

    /**
     * Передаёт текст ответного сообщения
     *
     * @param text текст входящего сообщения
     * @return ответного сообщения
     */
    @Override
    public String respond(String text) {
        String respond = recommend(text);

        log.info("Ответ на команду '/recommend' успешно сформирован.");
        return respond;
    }

    /**
     * Формирует текст ответного сообщения
     * <br>использует(@linc CommandHandler#extractFullName(text)) для извлечения имени и фамилии пользователя
     * <br>использует(@linc CommandHandler#getUserId(fullName)) для получения идентификатора пользователя
     * <br>использует(@linc UserRecommendation#getUserRecommendations(userId.get())) для получения доступных рекомендаций
     *
     * @param text текст входящего сообщения
     * @return сформированный текст ответного сообщения в зависимости от полученных параметров
     */
    private String recommend(String text) {
        log.info("Формирование ответа на команду '/recommend'...");

        String fullName = this.extractFullName(text);
        if (StringUtils.isBlank(fullName)) {
            log.warn("Введены некорректные данные.");
            return BotService.INCORRECT_DATA;
        } else {
            log.info("Имя и фамилия пользователя успешно извлечены.");
        }

        Optional<UUID> userId = this.getUserId(fullName);

        String userFullName = this.formatFullName(fullName);

        if (userId.isEmpty()) {
            log.warn("Пользователь не найден!");
            return "Пользователь " + userFullName + " не найден";
        }

        String greetings = "Здравствуйте " + userFullName + "!\n";

        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendations(userId.get());

        List<RecommendationData> userRecommendations = userRecommendation.getRecommendations();
        if (userRecommendations.isEmpty()) {
            log.warn("Рекомендаций не найдено!");
            return greetings + "К сожалению, в настоящий момент для Вас нет подходящих продуктов";
        } else {
            log.info("Рекомендации для пользователя успешно получены.");
            return greetings + "Новые продукты для Вас:\n" + this.recommendationsTextBuilder(userRecommendations);
        }
    }

    /**
     * Извлечение полного имени пользователя из текста команды.
     *
     * @param text текст входящего сообщения
     * @return полное имя пользователя.
     */
    public String extractFullName(String text) {
        log.info("Извлечение имени и фамилии пользователя из текста сообщения...");

        return StringUtils.substringAfter(text, " ");
    }

    /**
     * Получение идентификатора пользователя по его полному имени.
     *
     * @param fullName полное имя пользователя
     * @return идентификатор пользователя, если найден, иначе {@link Optional#empty()}.
     */
    public Optional<UUID> getUserId(String fullName) {
        log.info("Получение идентификатора пользователя...");

        List<User> users = this.getUserByFullName(fullName);
        if (users.size() == 1) {
            log.info("Пользователь найден.");
            log.info("Идентификатор пользователя успешно получен.");
            return Optional.of(users.get(0).getId());
        } else {
            log.warn("Ошибка получения идентификатора пользователя: найдено {}, ожидалось 1.", users.size());
            return Optional.empty();
        }
    }

    /**
     * Формирование строки с текстом рекомендаций для пользователя.
     *
     * @param userRecommendations список рекомендаций для пользователя
     * @return строка с текстом рекомендаций.
     */
    public String recommendationsTextBuilder(List<RecommendationData> userRecommendations) {
        log.info("Создание презентации рекомендаций для пользователя...");

        String recommendationPattern = "%s\n%s\n\n";
        StringBuilder builder = new StringBuilder();
        for (RecommendationData recommendationData : userRecommendations) {
            String recommendation = String.format(recommendationPattern, recommendationData.getProductName(), recommendationData.getProductText());
            builder.append(recommendation);
        }
        log.info("Презентация рекомендаций создана.");
        return builder.toString();
    }

    /**
     * Поиск пользователя в базе данных по имени и фамилии.
     *
     * @param fullName полное имя пользователя
     * @return список пользователей, чьи имена соответствуют полному имени.
     */
    private List<User> getUserByFullName(String fullName) {
        log.info("Поиск пользователя в базе данных по имени и фамилии...");
        String nameKey = this.extractNameKey(fullName);
        return userService.getUserByNameKey(nameKey)
                .stream()
                .filter(user -> {
                    String userFullName = user.getFullName();
                    return userFullName.equalsIgnoreCase(fullName);
                }).toList();
    }

    /**
     * Извлечение ключа для поиска из имени и фамилии пользователя.
     *
     * @param fullName полное имя пользователя
     * @return строка с ключом для поиска.
     */
    private String extractNameKey(String fullName) {
        log.info("Извлечение ключа для поиска из имени и фамилии пользователя...");
        String nameKey = StringUtils.substringBefore(fullName, " ") + "%";

        log.info("Ключ для поиска успешно извлечён.");
        return nameKey;
    }

    /**
     * Форматирование полного имени пользователя, делая каждое слово с заглавной буквы.
     *
     * @param fullName полное имя пользователя
     * @return отформатированное полное имя.
     */
    private String formatFullName(String fullName) {
        String[] formatFullName = Arrays.stream(StringUtils.split(fullName, " "))
                .map(s -> {
                    String f = s.toLowerCase();
                    return StringUtils.capitalize(f);
                })
                .toArray(String[]::new);
        String format = StringUtils.join(formatFullName, " ");

        log.debug("Отформатированное имя пользователя: '{}'", format);
        return format;
    }
}
