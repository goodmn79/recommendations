/*
Файл сервиса для выполнения команд в Телеграм-боте
Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.tgBot.command.Command;
import pro.sky.recommendations.tgBot.command.RecommendCommand;
import pro.sky.recommendations.tgBot.command.StartCommand;
import pro.sky.recommendations.tgBot.exceptions.BadMessageException;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandManager {
    private final Map<String, Command> commandMap;
    private final TelegramService telegramService;

    @Autowired
    public CommandManager(StartCommand startCommand, RecommendCommand recommendCommand, TelegramService telegramService) {
        this.telegramService = telegramService;
        // Инициализация мапы команд, где ключ - это строка команды, а значение - объект команды
        commandMap = new HashMap<>();
        commandMap.put("/start", startCommand);
        commandMap.put("/recommend", recommendCommand);
    }

    /**
     * Метод для выполнения команды по сообщению от пользователя
     *
     * @param UserMessage Сообщение, полученное от пользователя
     * @return Ответ от команды
     */

    public String executeCommand(String UserMessage, Long chatId) {
        String commandKey = UserMessage.split(" ")[0];
        Command command = commandMap.get(commandKey);
        if (command != null) {
            return command.execute(UserMessage, chatId);
        } else {
            String invalidCommandMessage = "Команда не найдена. Пожалуйста, используйте команду /start или /recommend.";
            telegramService.sendMessage(chatId, invalidCommandMessage);
            return invalidCommandMessage;
        }
    }
}
