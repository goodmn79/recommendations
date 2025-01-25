package pro.sky.recommendations.stats.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.model.Stats;

import java.util.List;

@Component
public class StatsMapper {
    private final Logger log = LoggerFactory.getLogger(StatsMapper.class);

    public StatsData toStatsData(Stats stats) {
        return new StatsData()
                .setRecommendationId(stats.getRecommendation().getId())
                .setCount(stats.getCount());
    }

    public List<StatsData> toStatsDataList(List<Stats> statsList) {
        log.info("Преобразование Stats.class в StatsData.class");
        return statsList.stream()
                .map(this::toStatsData)
                .toList();
    }
}
