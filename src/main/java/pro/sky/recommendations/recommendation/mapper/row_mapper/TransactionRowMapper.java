/*
 * Маппер строки результата SQL-запроса в объект {@link Transaction}.
 * Этот класс используется для преобразования строки из результата SQL-запроса в объект модели {@link Transaction}.
 * @author Powered by ©AYE.team
 * @version 1.0
 */

package pro.sky.recommendations.recommendation.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.model.Transaction;
import pro.sky.recommendations.recommendation.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class TransactionRowMapper implements RowMapper<Transaction> {

    /**
     * Преобразует строку из результата SQL-запроса в объект {@link Transaction}.
     * Этот метод извлекает данные из строки результата запроса и создает связанные сущности:
     * {@link User} и {@link Product}, а затем возвращает объект {@link Transaction}.
     *
     * @param rs     строка результата запроса, содержащая данные.
     * @param rowNum номер текущей строки в результате запроса (независимо от использования, может быть полезен для обработки).
     * @return объект {@link Transaction}, заполненный данными из текущей строки результата.
     * @throws SQLException если возникает ошибка при извлечении данных из {@link ResultSet}.
     */
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Создание объекта User из строки результата запроса
        User user = new User()
                .setId(rs.getObject("user_id", UUID.class))
                .setUserName(rs.getString("USERNAME"))
                .setFirstName(rs.getString("FIRST_NAME"))
                .setLastName(rs.getString("LAST_NAME"));

        // Создание объекта Product из строки результата запроса
        Product product = new Product()
                .setId(rs.getObject("product_id", UUID.class))
                .setName(rs.getString("NAME"))
                .setType(rs.getString("product_type"));

        // Создание и возврат объекта Transaction
        return new Transaction()
                .setId(rs.getObject("transaction_id", UUID.class))
                .setProduct(product)
                .setUser(user)
                .setType(rs.getString("transaction_type"))
                .setAmount(rs.getObject("AMOUNT", Integer.class));
    }
}
