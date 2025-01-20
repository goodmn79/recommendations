/*
Файл преобразования результата SQL-запроса в объект
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product()
                .setId(rs.getObject("ID", UUID.class))
                .setName(rs.getString("NAME"))
                .setType(rs.getString("TYPE"));
    }
}
