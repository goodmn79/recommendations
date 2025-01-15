/*
Класс для ответа на команду /recommend в Телеграм-боте
Powered by ©AYE.team
 */
package tgBot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tgBot.exceptions.BadMessageException;
import tgBot.service.RecommendationBotService;

@Component
public class RecommendCommand implements Command {
    private final RecommendationBotService recommendationBotService;

    @Autowired
    public RecommendCommand(RecommendationBotService recommendationBotService) {
        this.recommendationBotService = recommendationBotService;
    }

    @Override
    public String execute(String message) {
        try {
            return recommendationBotService.processMessage(message);
        } catch (BadMessageException e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
