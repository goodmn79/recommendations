package pro.sky.recommendations.tg_bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для инициализации бота Telegram.
 * <p>
 * Этот класс конфигурирует и создаёт экземпляр {@link TelegramBot}, который используется для взаимодействия с API Telegram.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Configuration
public class TelegramBotConfiguration {
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Создание экземпляра {@link TelegramBot}, настроенного с токеном для работы с Telegram API.
     *
     * @return {@link TelegramBot}.
     */
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(token);
    }
}
