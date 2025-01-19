/*
Файл регистрации слушателя для обновления сообщений
Powered by ©AYE.team
*/
package pro.sky.recommendations.tgBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.tgBot.service.RecommendationBotService;

import java.util.List;


@Component
public class UserRecommendationsBotUpdateListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(UserRecommendationsBotUpdateListener.class);

    @Autowired
    private RecommendationBotService botService;


    private final TelegramBot telegramBot;

    @Autowired
    public UserRecommendationsBotUpdateListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        list.forEach(update -> {
            logger.info("Processing update: {}", update);
            onUpdate(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void onUpdate(Update update) {
        Message message = update.message();
        if (message != null) {
            String userMessage = message.text();
            Long chatId = message.chat().id();
            logger.debug("ChatId: {}", chatId);
            botService.processMessage(userMessage, chatId);
        }
    }
}



