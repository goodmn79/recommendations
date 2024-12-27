package pro.sky.recommendations.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class RecommendationRowMapper implements RowMapper<Recommendation> {
    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Recommendation()
                .setId(rs.getObject("ID", UUID.class))
                .setName(rs.getString("NAME"))
                .setSpecification(rs.getString("DESCRIPTION"));
    }
}
