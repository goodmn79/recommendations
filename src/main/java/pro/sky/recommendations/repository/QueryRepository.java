package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.exception.DataRetentionException;
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
    public static final String EXIST_QUERIES_BY_RECOMMENDATION_ID_SQL = "SELECT EXISTS (SELECT 1 FROM QUERIES WHERE RECOMMENDATION_ID = ?)";
    public static final String DELETE_QUERIES_BY_RECOMMENDATION_ID_SQL = "DELETE FROM QUERIES WHERE RECOMMENDATION_ID = ?";

    private final JdbcTemplate jdbcTemplate;

    private final QueryRowMapper mapper;

    private final Logger log = LoggerFactory.getLogger(QueryRepository.class);

    public QueryRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                           QueryRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public Query save(Query query) {
        log.info("Invoke method 'QueryRepository: save");

        UUID recommendationId = query.getRecommendation().getId();

        try {
            jdbcTemplate.update(SAVE_QUERY_SQL, UUID.randomUUID(), recommendationId, query.getQuery(), query.argsToString(), query.getNegate());
        } catch (Exception e) {
            throw new DataRetentionException();
        }
        return query;
    }

    public List<Query> saveAll(List<Query> queries) {
        log.info("Invoke method 'QueryRepository: saveAll");

        try {
            jdbcTemplate.batchUpdate(SAVE_QUERY_SQL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Query query = queries.get(i);

                    ps.setObject(1, UUID.randomUUID());
                    ps.setObject(2, query.getRecommendation().getId());
                    ps.setString(3, query.getQuery());
                    ps.setString(4, query.argsToString());
                    ps.setBoolean(5, query.getNegate());
                }

                @Override
                public int getBatchSize() {
                    return queries.size();
                }
            });
            return queries;
        } catch (Exception e) {
            log.info(e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean isExistByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: isExistByRecommendationId");

        try {
            return Boolean.TRUE.equals(jdbcTemplate
                    .queryForObject(EXIST_QUERIES_BY_RECOMMENDATION_ID_SQL, Boolean.class, recommendationId));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Boolean.FALSE;
    }

    public List<Query> findAllByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: findAllByRecommendationId");

        try {
            return jdbcTemplate
                    .query(FIND_ALL_QUERIES_BY_RECOMMENDATION_ID_SQL, mapper, recommendationId);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Collections.emptyList();
    }

    public void deleteAllByRecommendationId(UUID recommendationId) {
        log.info("Invoke method 'QueryRepository: deleteAllByRecommendationId");

        try {
            jdbcTemplate.update(DELETE_QUERIES_BY_RECOMMENDATION_ID_SQL, recommendationId);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}