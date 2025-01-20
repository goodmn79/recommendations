/*
Файл репозитория для проверки и инициализации таблиц в базе данных recommendation.mv.db
Powered by ©AYE.team
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

    public DatabaseInitializer(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    @PostConstruct
    public void init() {
        log.info("Create tables 'RECOMMENDATIONS', 'QUERIES', 'STATISTICS' on database recommendations.mv...");

        try {
            String sql = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("schema.sql")).toURI())));

            jdbcTemplate.execute(sql);
            log.info("The tables were successfully created or already exist.");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
