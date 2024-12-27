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

import static pro.sky.recommendations.constant.SQLQuery.FIND_PRODUCT_BY_ID;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper mapper;

    Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    public ProductRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate,
                             ProductRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

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
