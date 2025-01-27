/*
 * Репозиторий для работы с таблицей PRODUCTS в базе данных.
 * Этот класс предоставляет методы для получения данных о банковских продуктах.
 * @author Powered by ©AYE.team
 * @version 1.0
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

    /**
     * Конструктор для инициализации репозитория.
     *
     * @param jdbcTemplate объект {@link JdbcTemplate}, используемый для работы с базой данных.
     * @param mapper       объект {@link ProductRowMapper}, который используется для преобразования результата SQL-запроса в объект {@link Product}.
     */
    public ProductRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate,
                             ProductRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    /**
     * Получение данных о банковском продукте по его идентификатору.
     *
     * @param id уникальный идентификатор продукта.
     * @return объект {@link Optional}, содержащий найденный продукт, если таковой существует, или пустой, если продукт не найден.
     */
    public Optional<Product> findById(UUID id) {
        log.debug("Вызван метод #findById.");

        String findProductByIdSql = "SELECT * FROM PRODUCTS WHERE ID = ?";

        try {
            // Выполнение SQL-запроса и преобразование результата в объект Product
            Product product = jdbcTemplate.queryForObject(findProductByIdSql, mapper, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            // Логирование ошибки, если продукт не найден или возникла другая ошибка
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
