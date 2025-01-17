/*
Файл репозитория для сохранения, получения и удаления данных из таблицы QUERIES, базы данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.mapper.row_mapper.QueryRowMapper;

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

    public QueryRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                           QueryRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    // Сохранение коллекции запросов для динамического правила рекомендации
    public void saveAll(List<Query> queries) {
        log.debug("Invoke method 'saveAll'");

        String saveQuerySql = "INSERT INTO QUERIES(ID, RECOMMENDATION_ID, QUERY, ARGUMENTS, NEGATE)VALUES (?, ?, ?, ?, ?)";

        UUID recommendationId = queries
                .stream()
                .map(query -> query.getRecommendation().getId())
                .findAny()
                .orElse(null);

        try {
            jdbcTemplate.batchUpdate(saveQuerySql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Query query = queries.get(i);

                    ps.setObject(1, UUID.randomUUID());
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
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // Получение коллекции запросов для динамического правила по идентификатору рекомендации
    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.debug("Invoke method 'findAllByRecommendationId'");

        String findAllQueriesByRecommendationIdSql = "SELECT * FROM QUERIES WHERE RECOMMENDATION_ID = ?";

        try {
            return jdbcTemplate
                    .query(findAllQueriesByRecommendationIdSql, mapper, recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    // Удаление коллекции запросов для динамического правила по идентификатору рекомендации
    public void deleteAllByRecommendationId(UUID recommendationId) {
        log.debug("Invoke method 'deleteAllByRecommendationId'");

        String deleteQueriesByRecommendationIdSql = "DELETE FROM QUERIES WHERE RECOMMENDATION_ID = ?";

        try {
            log.debug("Recommendation rule with recommendation id={} was successfully deleted", recommendationId);
            jdbcTemplate.update(deleteQueriesByRecommendationIdSql, recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
