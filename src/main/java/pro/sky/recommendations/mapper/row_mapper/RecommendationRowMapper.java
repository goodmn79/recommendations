package pro.sky.recommendations.mapper.row_mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.model.Product;
import pro.sky.recommendations.model.Recommendation;
import pro.sky.recommendations.service.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class RecommendationRowMapper implements RowMapper<Recommendation> {
    private final ProductService productService;

    public RecommendationRowMapper(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = productService.findById(rs.getObject("PRODUCT_ID", UUID.class));

        return new Recommendation()
                .setId(rs.getObject("ID", UUID.class))
                .setProduct(product)
                .setProductText(rs.getString("PRODUCT_TEXT"));
    }
}
