package pro.sky.recommendations.tg_bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Команда для получения инструкции по использованию бота.
 * <p>
 * Этот класс обрабатывает команду '/start', которая информирует пользователя о том, как получить рекомендации,
 * и направляет его к следующей команде с использованием '/recommend' с указанием имени и фамилии пользователя.
 * </p>
 * Реализует интерфейс {@link Command}.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Component
public class Start implements Command {
    private final Logger log = LoggerFactory.getLogger(Start.class);

    /**
     * Обрабатывает входящее сообщение и генерирует ответ с инструкцией для пользователя.
     *
     * @param text текст входящего сообщения, содержащий команду '/start'
     * @return текст ответного сообщения с инструкцией по использованию команды '/recommend'
     */
    @Override
    public String respond(String text) {
        log.info("Ответ на команду '/start' успешно сформирован.");

        return "Для получения информации о доступных Вам новых продуктах введите:\n/recommend <Имя Фамилия>";
    }
}
