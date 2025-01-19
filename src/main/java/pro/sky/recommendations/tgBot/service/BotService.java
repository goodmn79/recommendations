/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.service;


import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.tgBot.command.CommandHandler;

@Service
@RequiredArgsConstructor
public class BotService {
    public static final String INCORRECT_DATA = "Проверьте корректность введенных данных и повторите попытку";

    private final CommandHandler commandHandler;

    private static final Logger log = LoggerFactory.getLogger(BotService.class);

    // Генерация сообщений
    public String getUserRecommendations(Message message) {
        log.info("Генерация ответного сообщения...");

        String text = message.text();

        return commandHandler.getCommand(text)
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
}
