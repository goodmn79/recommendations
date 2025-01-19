/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Start implements Command {
    private final Logger log = LoggerFactory.getLogger(Start.class);

    /**
     * передаёт текст ответного сообщения
     *
     * @param text текст входящего сообщения
     * @return сообщение с инструкцией
     */
    @Override
    public String respond(String text) {
        log.info("Ответ на команду '/start' упешно сформирован.");

        return "Для получения информации о доступных Вам новых продуктах введите:\n/recommend <Имя Фамилия>";
    }
}
