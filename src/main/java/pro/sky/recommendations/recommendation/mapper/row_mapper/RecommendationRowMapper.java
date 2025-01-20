/*
Файл преобразования результата SQL-запроса в объект
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.mapper.row_mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.recommendation.model.Product;
import pro.sky.recommendations.recommendation.model.Query;
import pro.sky.recommendations.recommendation.model.Recommendation;
import pro.sky.recommendations.recommendation.service.ProductService;
import pro.sky.recommendations.recommendation.service.QueryService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RecommendationRowMapper implements RowMapper<Recommendation> {
    private final ProductService productService;
    private final QueryService queryService;

    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = productService.findById(rs.getObject("PRODUCT_ID", UUID.class));

        UUID id = rs.getObject("ID", UUID.class);

        List<Query> rule = queryService.findAllByRecommendationId(id);

        return new Recommendation()
                .setId(id)
                .setProduct(product)
                .setProductText(rs.getString("PRODUCT_TEXT"))
                .setRule(rule);

    }
}
