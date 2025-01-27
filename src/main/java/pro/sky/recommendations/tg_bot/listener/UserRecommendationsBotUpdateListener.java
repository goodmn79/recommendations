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
 * Слушатель обновлений для обработки сообщений Telegram-бота.
 * <p>
 * Этот сервис обрабатывает входящие обновления от пользователей, используя {@link TelegramBot}, и отвечает пользователям рекомендациями банковских продуктов.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */


@Service
@RequiredArgsConstructor
public class UserRecommendationsBotUpdateListener implements UpdatesListener {
    private final TelegramBot telegramBot;

    private final BotService botService;

    private final Logger log = LoggerFactory.getLogger(UserRecommendationsBotUpdateListener.class);

    /**
     * Инициализация слушателя обновлений и настройка бота для получения сообщений.
     * Этот метод вызывается после создания бина для настройки слушателя.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Обработка входящих обновлений (сообщений) от пользователей.
     * <p>
     * Для каждого обновления извлекается сообщение, а затем отправляется текст с рекомендациями пользователю.
     * </p>
     *
     * @param updates список обновлений, полученных от Telegram
     * @return код подтверждения обновлений, в данном случае {@link UpdatesListener#CONFIRMED_UPDATES_ALL}
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
