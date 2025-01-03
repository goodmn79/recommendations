/*
Файл репозитория для проверки и инициализации таблиц в базе данных recommendation.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static pro.sky.recommendations.constant.RecommendationDataBase.createTableQuery;

@Repository
@RequiredArgsConstructor
public class InitRepository {
    private final JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(InitRepository.class);

    // Создание таблицы в базе данных
    public void createTableIfNotExists(String tableName) {
        log.info("Creating table {}...", tableName);

        if (tableExists(tableName)) {
            try {
                jdbcTemplate.execute(createTableQuery(tableName));
                log.info("Table {} has been created", tableName);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    // Проверка существования таблицы в базе данных
    private boolean tableExists(String tableName) {
        log.info("Checking if table {} exists...", tableName);

        String createTableQuery = "SELECT EXISTS(SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE table_name = ?)AS table_exists;";

        boolean isExists = Boolean.TRUE.equals(jdbcTemplate.queryForObject(createTableQuery, Boolean.class, tableName));

        log.info("Checking the existence of a table {} - '{}'", tableName, isExists);
        return isExists;
    }
}
