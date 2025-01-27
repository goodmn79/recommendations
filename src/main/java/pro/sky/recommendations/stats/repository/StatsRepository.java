/**
 * Репозиторий для работы с таблицей статистики в базе данных.
 * Предназначен для получения, сохранения и очистки статистики использования рекомендаций.
 * @author Powered by ©AYE.team
 * @version 1.0
 */
package pro.sky.recommendations.stats.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.stats.mapper.StatsRowMapper;
import pro.sky.recommendations.stats.model.Stats;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class StatsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final StatsRowMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(StatsRepository.class);

    /**
     * Конструктор для инициализации репозитория.
     *
     * @param jdbcTemplate объект JdbcTemplate для взаимодействия с базой данных
     * @param mapper       маппер для преобразования данных из базы в объект Stats
     */
    public StatsRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate, StatsRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    /**
     * Получение всех статистических данных из таблицы STATISTICS.
     *
     * @return список всех статистик
     */
    public List<Stats> findAll() {
        log.debug("Вызван метод #findAll");

        String findAllStatsSql = "SELECT * FROM STATISTICS";
        try {
            return jdbcTemplate.query(findAllStatsSql, mapper);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Сохранение списка статистик в базу данных.
     * Все текущие записи статистики сначала очищаются.
     *
     * @param statsList список статистик для сохранения
     */
    public void saveAll(List<Stats> statsList) {
        log.debug("Вызван метод #saveAll");

        String saveStatsSql = "INSERT INTO STATISTICS (ID, RECOMMENDATION_ID, COUNT) VALUES (?, ?, ?)";

        // Очищаем старые данные перед вставкой новых
        this.clearStatistics();

        jdbcTemplate.batchUpdate(saveStatsSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Stats stats = statsList.get(i);
                ps.setObject(1, UUID.randomUUID());  // Генерация нового уникального ID для статистики
                ps.setObject(2, stats.getRecommendation().getId());
                ps.setInt(3, stats.getCount());
            }

            @Override
            public int getBatchSize() {
                return statsList.size();
            }
        });
    }

    /**
     * Очистка таблицы STATISTICS.
     * Этот метод удаляет все записи статистики из базы данных.
     */
    public void clearStatistics() {
        log.debug("Вызван метод #clearStatistics");

        String clearTableStatisticsSql = "TRUNCATE TABLE STATISTICS";
        jdbcTemplate.update(clearTableStatisticsSql);
    }
}
