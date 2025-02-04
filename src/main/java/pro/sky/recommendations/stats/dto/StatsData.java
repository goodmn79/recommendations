package pro.sky.recommendations.stats.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) для представления статистики по рекомендациям.
 * <p>
 * Этот класс используется для передачи данных о количестве применений конкретной рекомендации.
 * </P>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Accessors(chain = true)
public class StatsData {
    private UUID recommendationId;

    private int count;
}
