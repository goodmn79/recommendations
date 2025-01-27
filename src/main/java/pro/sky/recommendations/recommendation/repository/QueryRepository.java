/*
 * Репозиторий для работы с таблицей QUERIES в базе данных.
 * Этот класс предоставляет методы для сохранения, получения и удаления запросов,
 * связанных с динамическими правилами рекомендаций.
 * @author Powered by ©AYE.team
 * @version 1.0
 */

package pro.sky.recommendations.recommendation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.recommendation.mapper.row_mapper.QueryRowMapper;
import pro.sky.recommendations.recommendation.model.Query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class QueryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final QueryRowMapper mapper;

    private final Logger log = LoggerFactory.getLogger(QueryRepository.class);

    /**
     * Конструктор для инициализации репозитория.
     *
     * @param jdbcTemplate объект {@link JdbcTemplate}, используемый для работы с базой данных.
     * @param mapper       объект {@link QueryRowMapper}, который используется для преобразования результата SQL-запроса в объект {@link Query}.
     */
    public QueryRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                           QueryRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    /**
     * Сохраняет коллекцию запросов для динамического правила рекомендации.
     *
     * @param queries список объектов {@link Query}, которые необходимо сохранить.
     */
    public void saveAll(List<Query> queries) {
        log.debug("Вызван метод #saveAll.");

        String saveQuerySql = "INSERT INTO QUERIES(ID, RECOMMENDATION_ID, QUERY, ARGUMENTS, NEGATE)VALUES (?, ?, ?, ?, ?)";

        // Извлечение идентификатора рекомендации из первого запроса
        UUID recommendationId = queries
                .stream()
                .map(query -> query.getRecommendation().getId())
                .findAny()
                .orElse(null);

        // Выполнение пакетного обновления базы данных
        jdbcTemplate.batchUpdate(saveQuerySql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Query query = queries.get(i);

                // Заполнение параметров для SQL-запроса
                ps.setObject(1, UUID.randomUUID()); // Уникальный идентификатор для каждого запроса
                ps.setObject(2, recommendationId);
                ps.setString(3, query.getQuery());
                ps.setString(4, query.argsToString());
                ps.setBoolean(5, query.getNegate());
            }

            @Override
            public int getBatchSize() {
                return queries.size();
            }
        });
    }

    /**
     * Получение коллекции запросов для динамического правила по идентификатору рекомендации.
     *
     * @param recommendationId уникальный идентификатор рекомендации.
     * @return список объектов {@link Query}, связанных с указанной рекомендацией.
     */
    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.debug("Вызван метод #findAllByRecommendationId.");

        String findAllQueriesByRecommendationIdSql = "SELECT * FROM QUERIES WHERE RECOMMENDATION_ID = ?";

        try {
            // Выполнение SQL-запроса для получения всех запросов, связанных с рекомендацией
            return jdbcTemplate
                    .query(findAllQueriesByRecommendationIdSql, mapper, recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Удаление всех запросов, связанных с динамическим правилом по идентификатору рекомендации.
     *
     * @param recommendationId уникальный идентификатор рекомендации.
     */
    public void deleteAllByRecommendationId(UUID recommendationId) {
        log.debug("Вызван метод #deleteAllByRecommendationId.");

        String deleteQueriesByRecommendationIdSql = "DELETE FROM QUERIES WHERE RECOMMENDATION_ID = ?";

        // Удаление запросов для указанной рекомендации
        jdbcTemplate.update(deleteQueriesByRecommendationIdSql, recommendationId);
        log.debug("Правило для рекомендации с идентификатором '{}' успешно удалено", recommendationId);
    }
}
