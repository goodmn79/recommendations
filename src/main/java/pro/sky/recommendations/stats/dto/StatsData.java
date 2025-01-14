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
