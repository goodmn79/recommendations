/**
 * Сервис для проверки и инициализации таблиц в базе данных recommendation.mv.db
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
package pro.sky.recommendations.database.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;


@Service
public class DatabaseInitializer {
    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    /**
     * Конструктор для инициализации сервиса.
     *
     * @param jdbcTemplate JdbcTemplate для работы с базой данных рекомендаций
     */
    public DatabaseInitializer(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод инициализации, выполняющийся после создания бина.
     * Создает необходимые таблицы в базе данных: 'RECOMMENDATIONS', 'QUERIES', 'STATISTICS'.
     * Выполняет SQL-скрипт из файла schema.sql.
     */
    @PostConstruct
    public void init() {
        log.info("Инициализация таблиц 'RECOMMENDATIONS', 'QUERIES', 'STATISTICS'...");

        try {
            String sql = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.sql")).toURI())));

            jdbcTemplate.execute(sql);
            log.info("Инициализация таблиц успешно завершена.");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
