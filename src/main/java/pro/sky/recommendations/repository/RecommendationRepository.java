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
import pro.sky.recommendations.exception.SaveErrorException;
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
    public Recommendation save(Recommendation recommendation) {
        log.info("Saving recommendation...");

        UUID id = UUID.randomUUID();

        try {
            jdbcTemplate.update(SAVE_RECOMMENDATION_SQL, id, recommendation.getProduct().getId(), recommendation.getProductText());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return findById(id).map(r -> {
                    log.info("Recommendation successfully saved");
                    return r;
                }).orElseThrow(()->{
            log.warn("Recommendation save error");
            return new SaveErrorException();
        });
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public Optional<Recommendation> findById(UUID id) {
        log.info("Fetching recommendation...");

        try {
            log.info("Recommendation successfully found");
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_RECOMMENDATION_BY_ID_SQL, mapper, id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.warn("Recommendation not found");
        return Optional.empty();
    }

    // Получение коллекции рекомендаций банковских продуктов
    public List<Recommendation> findAll() {
        log.info("Fetching all recommendations...");

        try {
            log.info("Recommendations successfully found");
            return jdbcTemplate.queryForStream(FIND_ALL_RECOMMENDATION_SQL, mapper)
                    .toList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.warn("Recommendations not found");
        return Collections.emptyList();
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID id) {
        log.info("Deleting recommendation...");

        try {
            log.info("Recommendation was successfully deleted");
            jdbcTemplate.update(DELETE_RECOMMENDATION_BY_ID_SQL, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
