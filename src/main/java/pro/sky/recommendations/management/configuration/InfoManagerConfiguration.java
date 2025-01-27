/**
 * Конфигурационный класс для создания и настройки менеджера информации о сборке.
 * Использует свойства из файла build-info.properties.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
package pro.sky.recommendations.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pro.sky.recommendations.management.dto.InfoManager;


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
