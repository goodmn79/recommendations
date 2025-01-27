/**
 * DTO класс для хранения информации о сборке приложения.
 * Использует значения из конфигурационных свойств build.name и build.version.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
package pro.sky.recommendations.management.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;


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
