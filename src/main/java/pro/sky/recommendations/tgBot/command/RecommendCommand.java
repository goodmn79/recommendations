/*
Класс для ответа на команду /recommend в Телеграм-боте
Powered by ©AYE.team
 */
package pro.sky.recommendations.tgBot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.tgBot.exceptions.BadMessageException;
import pro.sky.recommendations.tgBot.service.RecommendationBotService;

@Component
public class RecommendCommand implements Command {
    private final RecommendationBotService recommendationBotService;

    @Autowired
    public RecommendCommand(@Lazy RecommendationBotService recommendationBotService) {
        this.recommendationBotService = recommendationBotService;
    }

    @Override
    public String execute(String message, Long chatId) {
        try {
            return recommendationBotService.processMessage(message, chatId);
        } catch (BadMessageException e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
