/*
Файл репозитория для сохранения, получения и удаления данных из таблицы RECOMMENDATIONS, базы данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.recommendation.mapper.row_mapper.RecommendationRowMapper;
import pro.sky.recommendations.recommendation.model.Recommendation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RecommendationRepository {
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

        String saveRecommendationSql = "INSERT INTO RECOMMENDATIONS (ID, PRODUCT_ID, PRODUCT_TEXT) VALUES (?, ?, ?)";

        jdbcTemplate.update(saveRecommendationSql, recommendation.getId(), recommendation.getProduct().getId(), recommendation.getProductText());
    }

    // Получение рекомендации банковского продукта по её идентификатору
    public Optional<Recommendation> findById(UUID id) {
        log.debug("Invoke method 'findById' with id {}", id);

        String findRecommendationByIdSql = "SELECT * FROM RECOMMENDATIONS WHERE ID = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(findRecommendationByIdSql, mapper, id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    // Получение коллекции рекомендаций банковских продуктов
    public List<Recommendation> findAll() {
        log.debug("Invoke method 'findAll'");

        String findAllRecommendationSql = "SELECT * FROM RECOMMENDATIONS";

        try {
            return jdbcTemplate.query(findAllRecommendationSql, mapper);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    // Удаление рекомендации банковского продукта по её идентификатору
    public void deleteById(UUID id) {
        log.debug("Deleting recommendation id={}", id);

        String deleteRecommendationByIdSql = "DELETE FROM RECOMMENDATIONS WHERE ID = ?";

        jdbcTemplate.update(deleteRecommendationByIdSql, id);
    }
}
