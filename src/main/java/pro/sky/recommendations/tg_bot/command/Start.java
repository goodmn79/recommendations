package pro.sky.recommendations.tg_bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Класс реализует интерфейс {@link Command} и предоставляет логику для обработки команды "/start".
 *
 * @author Powered by ©AYE.team
 */
@Component
public class Start implements Command {
    private final Logger log = LoggerFactory.getLogger(Start.class);

    /**
     * Обработка команды "/start".
     *
     * @param text текст команды, переданный пользователем.
     * @return строка с инструкциями для получения рекомендаций.
     */
    @Override
    public String respond(String text) {
        log.info("Ответ на команду '/start' успешно сформирован.");

        return "Для получения информации о доступных Вам новых продуктах введите:\n/recommend <Имя Фамилия>";
    }
}
