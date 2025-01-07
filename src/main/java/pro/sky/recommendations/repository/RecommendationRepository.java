/*
Файл репозитория для сохранения, получения и удаления данных из таблицы RECOMMENDATIONS, базы данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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

    // Сохранение рекомендации банковского продукта
    public void save(Recommendation recommendation) {
        log.debug("Invoke method 'save' for recommendation with id='{}'", recommendation.getId());

        jdbcTemplate.update(SAVE_RECOMMENDATION_SQL, recommendation.getId(), recommendation.getProduct().getId(), recommendation.getProductText());
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public Optional<Recommendation> findById(UUID id) {
        log.debug("Invoke method 'findById' with id {}", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_RECOMMENDATION_BY_ID_SQL, mapper, id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    // Получение коллекции рекомендаций банковских продуктов
    public List<Recommendation> findAll() {
        log.debug("Invoke method 'findAll'");

        try {
            return jdbcTemplate.query(FIND_ALL_RECOMMENDATION_SQL, mapper);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID id) {
        log.debug("Deleting recommendation id={}", id);

        jdbcTemplate.update(DELETE_RECOMMENDATION_BY_ID_SQL, id);
    }
}
