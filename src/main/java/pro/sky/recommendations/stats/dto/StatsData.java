/**
 * DTO (Data Transfer Object) для представления статистики по рекомендациям.
 * Этот класс используется для передачи данных о количестве применений конкретной рекомендации.
 * @author Powered by ©AYE.team
 * @version 1.0
 */
package pro.sky.recommendations.stats.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;


@Data
@Accessors(chain = true)
public class StatsData {

    private UUID recommendationId;

    private int count;
}
