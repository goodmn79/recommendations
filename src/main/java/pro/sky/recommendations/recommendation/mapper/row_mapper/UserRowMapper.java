package pro.sky.recommendations.recommendation.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Маппер строки результата SQL-запроса в объект {@link User}.
 * Этот класс используется для преобразования строки из результата SQL-запроса в объект модели {@link User}.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Component
public class UserRowMapper implements RowMapper<User> {

    /**
     * Преобразует строку из результата SQL-запроса в объект {@link User}.
     * Этот метод извлекает данные из строки результата запроса и создает объект {@link User}.
     *
     * @param rs     строка результата запроса, содержащая данные.
     * @param rowNum номер текущей строки в результате запроса (независимо от использования, может быть полезен для обработки).
     * @return объект {@link User}, заполненный данными из текущей строки результата.
     * @throws SQLException если возникает ошибка при извлечении данных из {@link ResultSet}.
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User()
                .setId(rs.getObject("ID", UUID.class))
                .setUserName(rs.getString("USERNAME"))
                .setFirstName(rs.getString("FIRST_NAME"))
                .setLastName(rs.getString("LAST_NAME"));
    }
}
