package pro.sky.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.row_mapper.QueryRowMapper;
import pro.sky.recommendations.model.Query;

import java.util.List;
import java.util.UUID;

import static pro.sky.recommendations.constant.SQLQuery.FIND_ALL_QUERIES_BY_DYNAMIC_RULE_ID_SQL;
import static pro.sky.recommendations.constant.SQLQuery.SAVE_QUERY_SQL;

@Repository
public class QueryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryRowMapper mapper;

    public QueryRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate,
                           QueryRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public void save(Query query) {
        UUID dynamicRuleId = query.getDynamicRule().getId();
        jdbcTemplate.update(SAVE_QUERY_SQL, query.getId(), dynamicRuleId, query.getQuery(), query.getNegate());
    }

    public List<Query> saveAll(List<Query> queries) {
        for (Query query : queries) {
            save(query);
        }
        return queries;
    }

    public List<Query> findAllByDynamicRuleId(UUID dynamicRuleId) {
        return jdbcTemplate.query(FIND_ALL_QUERIES_BY_DYNAMIC_RULE_ID_SQL, mapper, dynamicRuleId);
    }
}
