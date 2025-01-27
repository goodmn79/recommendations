package pro.sky.recommendations.stats.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.stats.dto.StatsData;
import pro.sky.recommendations.stats.model.Stats;

import java.util.List;

/**
 * Класс для преобразования объектов типа {@link Stats} в объекты типа {@link StatsData}.
 * Этот класс используется для маппинга данных статистики между слоями приложения.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@Component
public class StatsMapper {

    private final Logger log = LoggerFactory.getLogger(StatsMapper.class);

    /**
     * Преобразует объект типа {@link Stats} в объект типа {@link StatsData}.
     *
     * @param stats объект типа {@link Stats}, который нужно преобразовать
     * @return объект типа {@link StatsData}, содержащий информацию о рекомендации и количестве
     */
    public StatsData toStatsData(Stats stats) {
        return new StatsData()
                .setRecommendationId(stats.getRecommendation().getId())
                .setCount(stats.getCount());
    }

    /**
     * Преобразует список объектов типа {@link Stats} в список объектов типа {@link StatsData}.
     *
     * @param statsList список объектов типа {@link Stats}, которые нужно преобразовать
     * @return список объектов типа {@link StatsData}
     */
    public List<StatsData> toStatsDataList(List<Stats> statsList) {
        log.info("Преобразование Stats.class в StatsData.class");
        return statsList.stream()
                .map(this::toStatsData)
                .toList();
    }
}
