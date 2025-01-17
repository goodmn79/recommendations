/*
Файл сервиса для отправки сообщений в Телеграм-бот
Powered by ©AYE.team
 */
package pro.sky.recommendations.tgBot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramService {
    private final TelegramBot telegramBot;

    @Autowired
    public TelegramService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }
}

