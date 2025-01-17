/*
Файл репозитория для получения данных из таблицы PRODUCTS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.recommendation.mapper.row_mapper.ProductRowMapper;
import pro.sky.recommendations.recommendation.model.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper mapper;

    private final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    public ProductRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate,
                             ProductRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    // Получение данных о банковском продукте по его идентификатору
    public Optional<Product> findById(UUID id) {
        log.debug("Invoke method:'findById'");

        String findProductByIdSql = "SELECT * FROM PRODUCTS WHERE ID = ?";

        try {
            Product product = jdbcTemplate.queryForObject(findProductByIdSql, mapper, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}
