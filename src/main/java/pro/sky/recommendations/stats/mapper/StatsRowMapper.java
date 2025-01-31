package pro.sky.recommendations.stats.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.service.RecommendationService;
import pro.sky.recommendations.stats.model.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Маппер для преобразования строки результата запроса из базы данных в объект типа {@link Stats}.
 * Используется для маппинга данных статистики в объекты {@link Stats}.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class StatsRowMapper implements RowMapper<Stats> {
    private final RecommendationService recommendationService;

    /**
     * Преобразует строку результата запроса в объект типа {@link Stats}.
     *
     * @param rs     результат запроса из базы данных
     * @param rowNum номер строки (не используется)
     * @return объект типа {@link Stats}, полученный из строки результата запроса
     * @throws SQLException если происходит ошибка при извлечении данных из результата запроса
     */
    @Override
    public Stats mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID recommendationId = rs.getObject("RECOMMENDATION_ID", UUID.class);

        Recommendation recommendation = recommendationService.findById(recommendationId);

        return new Stats()
                .setId(rs.getObject("ID", UUID.class))
                .setRecommendation(recommendation)
                .setCount(rs.getInt("COUNT"));
    }
}
