/*
Класс для ответа на команду /start в Телеграм-боте
Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.tgBot.service.RecommendationBotService;
import pro.sky.recommendations.tgBot.service.TelegramSenderService;

@Component
public class StartCommand implements Command {

    private final TelegramSenderService telegramSenderService;
    public StartCommand(TelegramSenderService telegramSenderService) {
        this.telegramSenderService = telegramSenderService;
    }

    @Override
    public String execute(String message, Long chatId) {
        String welcomeMessage = "Для получения доступных Вам рекомендаций введите: '/recommend Имя Фамилия' (пример: /recommend Иван Иванов)";
        telegramSenderService.sendMessage(chatId, welcomeMessage);
        return welcomeMessage;
    }
}
