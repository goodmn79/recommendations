/*
Файл преобразования результата SQL-запроса в объект
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class QueryRowMapper implements RowMapper<Query> {
    @Override
    public Query mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Query()
                .setId(rs.getObject("ID", UUID.class))
                .setQuery(rs.getString("QUERY"))
                .stringToArgs(rs.getString("ARGUMENTS"))
                .setNegate(rs.getBoolean("NEGATE"));
    }
}
