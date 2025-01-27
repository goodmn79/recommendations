/**
 * Конфигурация для инициализации бота Telegram.
 * <p>
 * Этот класс конфигурирует и создаёт экземпляр {@link TelegramBot}, который используется для взаимодействия с API Telegram.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

package pro.sky.recommendations.tg_bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {
    @Value("${telegram.bot.token}")
    private String token;
    /**
     * Создаёт и возвращает экземпляр {@link TelegramBot}, который использует токен, указанный в конфигурации приложения.
     *
     * @return экземпляр {@link TelegramBot}, настроенный с указанным токеном
     */
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(token);
    }
}
