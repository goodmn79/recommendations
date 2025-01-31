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

/**
 * Маппер строки результата SQL-запроса в объект {@link Recommendation}.
 * <p>
 * Этот класс используется для преобразования строки из результата SQL-запроса в объект модели {@link Recommendation}.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Component
@RequiredArgsConstructor
public class RecommendationRowMapper implements RowMapper<Recommendation> {

    private final ProductService productService;
    private final QueryService queryService;

    /**
     * Преобразует строку из результата SQL-запроса в объект {@link Recommendation}.
     * <br>Этот метод извлекает данные из строки результата запроса и использует сервисы {@link ProductService} и {@link QueryService} для получения связанных сущностей: {@link Product} и {@link Query}.
     *
     * @param rs     строка результата запроса, содержащая данные.
     * @param rowNum номер текущей строки в результате запроса (независимо от использования, может быть полезен для обработки).
     * @return объект {@link Recommendation}, заполненный данными из текущей строки результата.
     * @throws SQLException если возникает ошибка при извлечении данных из {@link ResultSet}.
     */
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
