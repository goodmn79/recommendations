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

/**
 * Репозиторий для работы с таблицей RECOMMENDATIONS в базе данных.
 * Этот класс предоставляет методы для сохранения, получения и удаления рекомендаций для банковских продуктов.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Repository
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RecommendationRowMapper mapper;

    private final Logger log = LoggerFactory.getLogger(RecommendationRepository.class);

    /**
     * Конструктор для инициализации репозитория.
     *
     * @param jdbcTemplate объект {@link JdbcTemplate}, используемый для работы с базой данных.
     * @param mapper       объект {@link RecommendationRowMapper}, который используется для преобразования результата SQL-запроса в объект {@link Recommendation}.
     */
    public RecommendationRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                                    RecommendationRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    /**
     * Сохраняет рекомендацию банковского продукта.
     *
     * @param recommendation объект {@link Recommendation}, который необходимо сохранить в базе данных.
     */
    public void save(Recommendation recommendation) {
        log.debug("Вызван метод #save");

        String saveRecommendationSql = "INSERT INTO RECOMMENDATIONS (ID, PRODUCT_ID, PRODUCT_TEXT) VALUES (?, ?, ?)";

        jdbcTemplate.update(saveRecommendationSql, recommendation.getId(), recommendation.getProduct().getId(), recommendation.getProductText());
    }

    /**
     * Получение рекомендации банковского продукта по её идентификатору.
     *
     * @param id уникальный идентификатор рекомендации.
     * @return объект {@link Optional}, который может содержать найденную рекомендацию.
     */
    public Optional<Recommendation> findById(UUID id) {
        log.debug("Вызван метод #findById");

        String findRecommendationByIdSql = "SELECT * FROM RECOMMENDATIONS WHERE ID = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(findRecommendationByIdSql, mapper, id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Получение всех рекомендаций банковских продуктов.
     *
     * @return список всех объектов {@link Recommendation} из базы данных.
     */
    public List<Recommendation> findAll() {
        log.debug("Вызван метод #findAll");

        String findAllRecommendationSql = "SELECT * FROM RECOMMENDATIONS";

        try {
            return jdbcTemplate.query(findAllRecommendationSql, mapper);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Удаление рекомендации банковского продукта по её идентификатору.
     *
     * @param id уникальный идентификатор рекомендации.
     */
    public void deleteById(UUID id) {
        log.debug("Вызван метод #deleteById");

        String deleteRecommendationByIdSql = "DELETE FROM RECOMMENDATIONS WHERE ID = ?";

        jdbcTemplate.update(deleteRecommendationByIdSql, id);
    }
}
