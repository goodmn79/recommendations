package pro.sky.recommendations.stats.mapper.rowMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.service.RecommendationService;
import pro.sky.recommendations.stats.model.Stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class StatsRowMapper implements RowMapper<Stats> {

    private final RecommendationService recommendationService;


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
