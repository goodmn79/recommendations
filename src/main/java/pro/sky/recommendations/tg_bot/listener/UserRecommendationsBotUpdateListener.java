package pro.sky.recommendations.tg_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.tg_bot.service.BotService;

import java.util.List;

/**
 * Обработчик обновлений для бота, который отвечает на запросы пользователей с рекомендациями.
 *
 * @author Powered by ©AYE.team
 * @see BotService
 */
@Service
@RequiredArgsConstructor
public class UserRecommendationsBotUpdateListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    private final BotService botService;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationsBotUpdateListener.class);

    /**
     * Инициализация обработчика обновлений. Метод вызывается после создания бина.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обработка входящих обновлений от Telegram.
     *
     * @param updates список обновлений, содержащий сообщения от пользователей.
     * @return {@link UpdatesListener#CONFIRMED_UPDATES_ALL} для подтверждения обработки всех обновлений.
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Обработка данных...");
            Message message = update.message();
            long chatId = message.chat().id();
            String messageText = botService.getUserRecommendations(message);
            telegramBot.execute(new SendMessage(chatId, messageText));
        });
        log.info("Обработка данных успешно завершена.");

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
