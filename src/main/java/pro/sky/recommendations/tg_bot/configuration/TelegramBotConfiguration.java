/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tg_bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для настройки бота Telegram.
 */
@Configuration
public class TelegramBotConfiguration {

    /**
     * Токен для доступа к Telegram API.
     * Значение токена загружается из конфигурационного файла {@code application.properties} с помощью аннотации {@link Value}.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создание экземпляра, настроенного с токеном для работы с Telegram API.
     * @return {@link TelegramBot}.
     */
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(token);
    }
}
