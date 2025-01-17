/*
Класс для ответа на команду /start в Телеграм-боте
Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Override
    public String execute(String message, Long chatId) {
        return "Для получения доступных Вам рекомендаций введите: '/recommend <Имя Фамилия>' (пример: /recommend <Иван Иванов>)";
    }
}
