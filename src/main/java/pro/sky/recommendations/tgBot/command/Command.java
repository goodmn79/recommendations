/*
Файл интерфейса для команд в Телеграм-боте
Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

public interface Command {
    String execute(String message, Long chatId);
}
