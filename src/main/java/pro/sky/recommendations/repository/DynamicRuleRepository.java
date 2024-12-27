package pro.sky.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.model.DynamicRule;

import java.util.UUID;

import static pro.sky.recommendations.constant.SQLQuery.SAVE_DYNAMIC_RULE_SQL;

@Repository
public class DynamicRuleRepository {
    private final JdbcTemplate jdbcTemplate;

    public DynamicRuleRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(DynamicRule dynamicRule) {
        UUID productId = dynamicRule.getProduct().getId();
        jdbcTemplate.update(SAVE_DYNAMIC_RULE_SQL, dynamicRule.getId(), productId, dynamicRule.getSpecification());
    }
}
