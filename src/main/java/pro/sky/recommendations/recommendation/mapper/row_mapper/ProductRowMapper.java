package pro.sky.recommendations.recommendation.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/*
 * Маппер строки результата SQL-запроса в объект {@link Product}.
 * Этот класс используется для преобразования строки из результата SQL-запроса в объект модели {@link Product}.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Component
public class ProductRowMapper implements RowMapper<Product> {

    /**
     * Преобразует строку из результата SQL-запроса в объект {@link Product}.
     * Используется для маппинга полей результата запроса в свойства объекта {@link Product}.
     *
     * @param rs     строка результата запроса, содержащая данные.
     * @param rowNum номер текущей строки в результате запроса (независимо от использования, может быть полезен для обработки).
     * @return объект {@link Product}, заполненный данными из текущей строки результата.
     * @throws SQLException если возникает ошибка при извлечении данных из {@link ResultSet}.
     */
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product()
                .setId(rs.getObject("ID", UUID.class))
                .setName(rs.getString("NAME"))
                .setType(rs.getString("TYPE"));
    }
}
