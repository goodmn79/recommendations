/*
Класс для ответа на команду /start в Телеграм-боте
Powered by ©AYE.team
 */

package tgBot.command;

import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Override
    public String execute(String message) {
        return "Отправьте команду /recommend, а также свое Имя и Фамилию через пробел для получения рекомендаций. Например, \n/recommend Ivan Ivanov";
    }
}
