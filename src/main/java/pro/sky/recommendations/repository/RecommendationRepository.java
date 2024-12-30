package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.exception.DataRetentionException;
import pro.sky.recommendations.mapper.row_mapper.RecommendationRowMapper;
import pro.sky.recommendations.model.Recommendation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RecommendationRepository {
    public static final String SAVE_RECOMMENDATION_SQL = "INSERT INTO RECOMMENDATIONS (ID, PRODUCT_ID, PRODUCT_TEXT) VALUES (?, ?, ?)";
    public static final String FIND_RECOMMENDATION_BY_ID_SQL = "SELECT * FROM RECOMMENDATIONS WHERE ID = ?";
    public static final String FIND_ALL_RECOMMENDATION_SQL = "SELECT * FROM RECOMMENDATIONS";
    public static final String DELETE_RECOMMENDATION_BY_ID_SQL = "DELETE FROM RECOMMENDATIONS WHERE ID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RecommendationRowMapper mapper;

    private final Logger log = LoggerFactory.getLogger(RecommendationRepository.class);

    public RecommendationRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                                    RecommendationRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public Recommendation save(Recommendation recommendation) {
        log.info("Invoke method 'RecommendationRepository.save'");

        UUID id = UUID.randomUUID();

        try {
            jdbcTemplate.update(SAVE_RECOMMENDATION_SQL, id, recommendation.getProduct().getId(), recommendation.getProductText());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DataRetentionException();
        }
        return recommendation
                .setId(id);
    }

    public Optional<Recommendation> findById(UUID id) {
        log.info("Invoke method 'RecommendationRepository.findById'");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_RECOMMENDATION_BY_ID_SQL, mapper, id));
        } catch (Exception e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    public List<Recommendation> findAll() {
        log.info("Invoke method 'RecommendationRepository.findAll'");

        try {
            return jdbcTemplate.queryForStream(FIND_ALL_RECOMMENDATION_SQL, mapper)
                    .toList();
        } catch (Exception e) {
            log.info(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void deleteById(UUID id) {
        log.info("Invoke method 'RecommendationRepository.deleteById'");

        try {
            jdbcTemplate.update(DELETE_RECOMMENDATION_BY_ID_SQL, id);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
