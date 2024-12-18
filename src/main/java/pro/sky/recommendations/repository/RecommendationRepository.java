package pro.sky.recommendations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.model.Recommendation;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Recommendation> mapper;

    Optional<Recommendation> findByName(String name) {
        String query = "SELECT * FROM RECOMMENDATIONS WHERE NAME = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, mapper, name));
    }
}
