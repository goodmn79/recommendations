package pro.sky.recommendations.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class TransactionRowMapper implements RowMapper <Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User()
                .setId(rs.getObject("user_id", UUID.class))
                .setUserName(rs.getString("USERNAME"))
                .setFirstName(rs.getString("FIRST_NAME"))
                .setLastName(rs.getString("LAST_NAME"));

        Product product = new Product()
                .setId(rs.getObject("product_id", UUID.class))
                .setName(rs.getString("NAME"))
                .setType(rs.getString("product_type"));

        return new Transaction()
                .setId(rs.getObject("transaction_id", UUID.class))
                .setProduct(product)
                .setUser(user)
                .setType(rs.getString("transaction_type"))
                .setAmount(rs.getObject("AMOUNT", Integer.class));
    }
}
