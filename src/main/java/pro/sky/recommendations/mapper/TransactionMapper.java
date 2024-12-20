package pro.sky.recommendations.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.model.Transaction;
import pro.sky.recommendations.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class TransactionMapper implements RowMapper <Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User()
                .setId(rs.getObject("user_id", UUID.class))
                .setUserName(rs.getString("user_username"))
                .setFirstName(rs.getString("user_first_name"))
                .setLastName(rs.getString("user_last_name"));

        Product product = new Product()
                .setId(rs.getObject("product_id", UUID.class))
                .setName(rs.getString("product_name"))
                .setType(rs.getString("product_type"));

        return new Transaction()
                .setId(rs.getObject("transaction_id", UUID.class))
                .setProduct(product)
                .setUser(user)
                .setType(rs.getString("transaction_type"))
                .setAmount(rs.getObject("transaction_amount", Integer.class));
    }
}
