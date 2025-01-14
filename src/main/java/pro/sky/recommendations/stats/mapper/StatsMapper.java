package pro.sky.recommendations.stats.mapper;

import org.springframework.stereotype.Component;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.model.Stats;

import java.util.List;

@Component
public class StatsMapper {
    public StatsData toStatsData(Stats stats) {
        return new StatsData()
                .setRecommendationId(stats.getRecommendation().getId())
                .setCount(stats.getCount());
    }

    public List<StatsData> toStatsDataList(List<Stats> statsList) {
        return statsList.stream()
                .map(this::toStatsData)
                .toList();
    }
}
