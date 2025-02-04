package pro.sky.recommendations.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pro.sky.recommendations.management.dto.InfoManager;

/**
 * Конфигурационный класс для создания и настройки менеджера информации о сборке.
 * <p>
 * Использует свойства из файла build-info.properties.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Configuration
@PropertySource("classpath:build-info.properties")
public class InfoManagerConfiguration {

    /**
     * Создает бин InfoManager для управления информацией о сборке приложения.
     *
     * @return Новый экземпляр InfoManager
     */
    @Bean
    public InfoManager infoManager() {
        return new InfoManager();
    }
}
