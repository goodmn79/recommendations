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
 * Сервис для обработки и генерации ответных сообщений для Telegram-бота.
 * <p>
 * Этот сервис анализирует входящие сообщения, извлекает команду и передает её соответствующему обработчику команд для генерации ответного сообщения.
 * Если команда неизвестна или данные некорректны, будет возвращено сообщение об ошибке.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class BotService {
    public static final String INCORRECT_DATA = "Проверьте корректность введенных данных и повторите попытку";

    private final Map<String, Command> commands;

    private static final Logger log = LoggerFactory.getLogger(BotService.class);

    /**
     * Генерация ответного сообщения для пользователя на основе входящего сообщения.
     * <p>
     * Сначала извлекается команда из текста сообщения, затем она передается соответствующему обработчику команд.
     * В случае ошибки или неизвестной команды пользователю будет отправлено сообщение о неправильных данных.
     * </p>
     *
     * @param message входящее сообщение от пользователя
     * @return текст ответного сообщения, который будет отправлен пользователю
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
    private Optional<Command> getCommand(String text) {
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
