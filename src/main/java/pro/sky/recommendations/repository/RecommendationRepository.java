package pro.sky.recommendations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.model.Recommendation;

import java.util.Optional;

import static pro.sky.recommendations.repository.constant.SQLQuery.FIND_RECOMMENDATION_BY_NAME;

@Repository
@RequiredArgsConstructor
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Recommendation> mapper;

    public Optional<Recommendation> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_RECOMMENDATION_BY_NAME, mapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
