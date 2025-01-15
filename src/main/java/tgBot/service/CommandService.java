/*
Файл сервиса для выполнения команд в Телеграм-боте
Powered by ©AYE.team
 */

package tgBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgBot.command.Command;
import tgBot.command.RecommendCommand;
import tgBot.command.StartCommand;
import tgBot.exceptions.BadMessageException;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandService {
    private final Map<String, Command> commandMap;

    @Autowired
    public CommandService(StartCommand startCommand, RecommendCommand recommendCommand) {
        // Инициализация мапы команд, где ключ - это строка команды, а значение - объект команды
        commandMap = new HashMap<>();
        commandMap.put("/start", startCommand);
        commandMap.put("/recommend", recommendCommand);
    }

    /**
     * Метод для выполнения команды по сообщению от пользователя
     *
     * @param message Сообщение, полученное от пользователя
     * @return Ответ от команды
     */

    public String executeCommand(String message) {
        String commandKey = message.split(" ")[0];
        Command command = commandMap.get(commandKey);
        if (command != null) {
            return command.execute(message);
        } else {
            throw new BadMessageException("Команда не найдена. Пожалуйста, используйте команду /start или /recommend.");
        }
    }
}
