package pro.sky.recommendations.management.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

/**
 * DTO класс для хранения информации о сборке приложения.
 * <p>
 * Использует значения из конфигурационных свойств build.name и build.version.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
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
