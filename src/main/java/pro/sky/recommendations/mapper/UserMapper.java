package pro.sky.recommendations.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User()
                .setId(rs.getObject("u.ID", UUID.class))
                .setUserName(rs.getString("u.USERNAME"))
                .setFirstName(rs.getString("u.FIRST_NAME"))
                .setLastName(rs.getString("u.LAST_NAME"));
    }
}
