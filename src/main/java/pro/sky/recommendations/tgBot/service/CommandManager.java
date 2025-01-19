/*
Файл сервиса для выполнения команд в Телеграм-боте
Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.tgBot.command.Command;
import pro.sky.recommendations.tgBot.command.RecommendCommand;
import pro.sky.recommendations.tgBot.command.StartCommand;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandManager {
    private final Map<String, Command> commandMap;
    private final TelegramSenderService telegramSenderService;

    private final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    @Autowired
    public CommandManager(StartCommand startCommand, RecommendCommand recommendCommand, TelegramSenderService telegramSenderService) {
        this.telegramSenderService = telegramSenderService;
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
        logger.debug("Received command: {}", commandKey);
        Command command = commandMap.get(commandKey);
        if (command != null) {
            return command.execute(UserMessage, chatId);
        } else {
            String invalidCommandMessage = "Команда не найдена. Пожалуйста, используйте команду /start или /recommend.";
            telegramSenderService.sendMessage(chatId, invalidCommandMessage);
            return invalidCommandMessage;
        }
    }
}
