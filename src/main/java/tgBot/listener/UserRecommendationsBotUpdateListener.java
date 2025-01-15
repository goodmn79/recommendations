/*
Файл регистрации слушателя для обновления сообщений
Powered by ©AYE.team
*/
package tgBot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tgBot.service.CommandService;
import tgBot.service.RecommendationBotService;

import java.util.List;


@Component
public class UserRecommendationsBotUpdateListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(UserRecommendationsBotUpdateListener.class);

    @Autowired
    private RecommendationBotService botService;
    @Autowired
    private CommandService commandService;


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

            String response = commandService.executeCommand(userMessage);
            sendMessage(chatId, response);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage request = new SendMessage(chatId, text);
        telegramBot.execute(request);
    }
}



