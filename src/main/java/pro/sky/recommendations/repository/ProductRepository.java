/*
Файл репозитория для получения данных из таблицы PRODUCTS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.row_mapper.ProductRowMapper;
import pro.sky.recommendations.model.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepository {
    public static final String FIND_PRODUCT_BY_ID = "SELECT * FROM PRODUCTS WHERE ID = ?";

    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    public ProductRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate,
                             ProductRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    // Получение данных о банковском продукте по его идентификатору
    public Optional<Product> findById(UUID id) {
        try {
            Product product = jdbcTemplate.queryForObject(FIND_PRODUCT_BY_ID, mapper, id);
            logger.info("Product found: {}", product);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Product with id '{}' not found", id);
            return Optional.empty();
        }
    }
}
