/*
Файл репозитория для сохранения, получения и удаления данных из таблицы QUERIES, базы данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.row_mapper.QueryRowMapper;
import pro.sky.recommendations.model.Query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class QueryRepository {
    public static final String SAVE_QUERY_SQL = "INSERT INTO QUERIES(ID, RECOMMENDATION_ID, QUERY, ARGUMENTS, NEGATE)VALUES (?, ?, ?, ?, ?)";
    public static final String FIND_ALL_QUERIES_BY_RECOMMENDATION_ID_SQL = "SELECT * FROM QUERIES WHERE RECOMMENDATION_ID = ?";
    public static final String DELETE_QUERIES_BY_RECOMMENDATION_ID_SQL = "DELETE FROM QUERIES WHERE RECOMMENDATION_ID = ?";

    private final JdbcTemplate jdbcTemplate;

    private final QueryRowMapper mapper;

    private final Logger log = LoggerFactory.getLogger(QueryRepository.class);

    public QueryRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                           QueryRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    // Сохранение коллекции запросов для динамического правила рекомендации
    public List<Query> saveAll(List<Query> queries) {
        log.info("Invoke method 'QueryRepository: saveAll");

        UUID recommendationId = queries
                .stream()
                .map(query -> query.getRecommendation().getId())
                .findAny()
                .orElse(null);

        try {
            jdbcTemplate.batchUpdate(SAVE_QUERY_SQL, new BatchPreparedStatementSetter() {
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

            return findAllByRecommendationId(recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    // Получение коллекции запросов для динамического правила по идентификатору рекомендации
    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: findAllByRecommendationId");

        try {
            return jdbcTemplate
                    .query(FIND_ALL_QUERIES_BY_RECOMMENDATION_ID_SQL, mapper, recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    // Удаление коллекции запросов для динамического правила по идентификатору рекомендации
    public void deleteAllByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: deleteAllByRecommendationId");

        try {
            jdbcTemplate.update(DELETE_QUERIES_BY_RECOMMENDATION_ID_SQL, recommendationId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
