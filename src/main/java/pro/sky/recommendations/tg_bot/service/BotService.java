package pro.sky.recommendations.tg_bot.service;


import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.tg_bot.command.Command;

import java.util.Map;
import java.util.Optional;

/**
 * Сервис для обработки команд телеграм-бота и генерации рекомендаций пользователю.
 *
 * @author Powered by ©AYE.team
 * @see Map
 * @see Command
 */
@Service
@RequiredArgsConstructor
public class BotService {

    public static final String INCORRECT_DATA = "Проверьте корректность введенных данных и повторите попытку";

    private final Map<String, Command> commands;

    private static final Logger log = LoggerFactory.getLogger(BotService.class);

    /**
     * Генерация рекомендаций для пользователя на основе команды из сообщения.
     *
     * @param message сообщение от пользователя, содержащее текст команды.
     * @return строка с рекомендацией или сообщение об ошибке, если команда не найдена.
     */
    public String getUserRecommendations(Message message) {
        log.info("Генерация ответного сообщения...");

        String text = message.text();

        return getCommand(text)
                .map(command -> {
                    String respond = command.respond(text);
                    log.info("Ответное сообщение успешно сгенерировано.");
                    return respond;
                })
                .orElseGet(() -> {
                    log.warn("Неизвестная команда!");
                    return INCORRECT_DATA;
                });
    }

    /**
     * Извлечение команды из текста сообщения.
     *
     * @param text текст сообщения от пользователя.
     * @return объект Optional, содержащий команду, если она найдена, или пустой Optional, если команда не найдена.
     */
    public Optional<Command> getCommand(String text) {
        if (StringUtils.isBlank(text)) return Optional.empty();
        String command;
        if (text.contains(" ")) {
            command = StringUtils.substringBetween(text, "/", " ");
        } else {
            command = StringUtils.substringAfter(text, "/");
        }
        log.info("Получена команда: '{}'", command);

        return Optional.ofNullable(commands.get(command));
    }
}
