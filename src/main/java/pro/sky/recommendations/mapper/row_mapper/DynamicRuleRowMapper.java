package pro.sky.recommendations.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.DynamicRule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class DynamicRuleRowMapper implements RowMapper<DynamicRule> {
    @Override
    public DynamicRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DynamicRule()
                .setId(rs.getObject("ID", UUID.class))
                .setSpecification(rs.getString("SPECIFICATION"));
    }
}
