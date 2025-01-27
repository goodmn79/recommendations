package pro.sky.recommendations.management.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

/**
 * DTO класс для хранения информации о сборке приложения.
 * Использует значения из конфигурационных свойств build.name и build.version.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@Data
@Accessors(chain = true)
public class InfoManager {
    /**
     * Название сборки приложения.
     */
    @Value("${build.name}")
    private String name;

    /**
     * Версия сборки приложения.
     */
    @Value("${build.version}")
    private String version;
}
