/*
Файл репозитория для проверки и инициализации таблиц в базе данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InitRepository {
    private static final String CREATE_TABLE_RECOMMENDATIONS = "CREATE TABLE IF NOT EXISTS RECOMMENDATIONS (ID UUID PRIMARY KEY, PRODUCT_ID   UUID NOT NULL UNIQUE, PRODUCT_TEXT TEXT NOT NULL)";
    private static final String CREATE_TABLE_QUERIES = "CREATE TABLE IF NOT EXISTS QUERIES (ID UUID PRIMARY KEY, RECOMMENDATION_ID   UUID NOT NULL, QUERY VARCHAR(100) NOT NULL, ARGUMENTS VARCHAR(255), NEGATE BOOLEAN NOT NULL)";

    private final JdbcTemplate jdbcTemplate;

    public InitRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final Logger log = LoggerFactory.getLogger(InitRepository.class);

    // Создание таблицы RECOMMENDATIONS в базе данных
    @PostConstruct
    private void createTableRecommendationsIfNotExists() {
        log.info("Creating table \"RECOMMENDATIONS\"...");

        if (tableExists("RECOMMENDATIONS")) {
            log.info("Table \"RECOMMENDATIONS\" already exists.");
        } else {
            try {
                jdbcTemplate.execute(CREATE_TABLE_RECOMMENDATIONS);
                log.info("Table \"RECOMMENDATIONS\" has been created");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    // Создание таблицы QUERIES в базе данных
    @PostConstruct
    private void createTableQueriesIfNotExists() {
        log.info("Creating table \"QUERIES\"...");

        if (tableExists("QUERIES")) {
            log.info("Table \"QUERIES\" already exists.");
        } else {
            try {
                jdbcTemplate.execute(CREATE_TABLE_QUERIES);
                log.info("Table \"QUERIES\" has been created");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    // Проверка существования таблицы в базе данных
    private boolean tableExists(String tableName) {
        log.debug("Checking if table \"{}\" exists...", tableName);


        String createTableQuery = "SELECT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE table_name = ?)AS table_exists;";

        boolean isExists = Boolean.TRUE.equals(jdbcTemplate.queryForObject(createTableQuery, Boolean.class, tableName));

        log.debug("Checking the existence of a table \"{}\" - '{}'", tableName, isExists);
        return isExists;
    }
}
