package pro.sky.recommendations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.ProductMapper;
import pro.sky.recommendations.model.Product;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductMapper mapper;

    public Product findByProductId(UUID id) {
        return jdbcTemplate.queryForObject("SELECT * FROM PRODUCTS WHERE ID = ?", mapper, id);
    }
}
