/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.dto.RecommendationData;
import pro.sky.recommendations.model.User;
import pro.sky.recommendations.service.UserRecommendationService;
import pro.sky.recommendations.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static pro.sky.recommendations.tgBot.command.RecommendCommand.RECOMMEND;
import static pro.sky.recommendations.tgBot.command.StartCommand.START;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final UserRecommendationService userRecommendationService;
    private final UserService userService;

    private Map<String, Command> commands;

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @PostConstruct
    private void commandsInit() {
        log.info("Инициализация команд телеграм-бота...");

        this.commands = Map.ofEntries(
                Map.entry(START, new StartCommand()),
                Map.entry(RECOMMEND, new RecommendCommand(this, userRecommendationService))
        );
        log.info("Инициализация команд телеграм-бота выполнена успешно.");
    }

    public Optional<Command> getCommand(String text) {
        String command = StringUtils.substringBefore(text, " ");

        return Optional.ofNullable(commands.get(command));
    }

    public String extractFullName(String text) {
        log.info("Извлечение имени и фамилии пользователя из текста сообщения...");

        return StringUtils.substringAfter(text, " ");
    }

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

    // Создание текста рекомендации
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

    private String extractNameKey(String fullName) {
        log.info("Извлечение ключа для поиска из имени и фамилии пользователя...");
        String nameKey = StringUtils.substringBefore(fullName, " ") + "%";

        log.info("Ключ для поиска успешно извлечён.");
        return nameKey;
    }
}
