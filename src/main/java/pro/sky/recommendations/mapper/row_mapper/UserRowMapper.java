/*
Файл преобразования результата SQL-запроса в объект
Powered by ©AYE.team
 */

package pro.sky.recommendations.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User()
                .setId(rs.getObject("ID", UUID.class))
                .setUserName(rs.getString("USERNAME"))
                .setFirstName(rs.getString("FIRST_NAME"))
                .setLastName(rs.getString("LAST_NAME"));
    }
}
