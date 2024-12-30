package pro.sky.recommendations.mapper.row_mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.service.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DynamicRuleRowMapper implements RowMapper<Recommendation> {
    private final ProductService productService;

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {

        UUID productId = rs.getObject("PRODUCT_ID", UUID.class);

        Product product = productService.findById(productId);

        return new Recommendation()
                .setId(rs.getObject("ID", UUID.class))
                .setProduct(product)
                .setProductText(rs.getString("SPECIFICATION"));
    }
}
