package pro.sky.recommendations.stats.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.stats.mapper.rowMapper.StatsRowMapper;
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

    Logger log = LoggerFactory.getLogger(StatsRepository.class);

    public StatsRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate, StatsRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public List<Stats> findAll() {
        String findAllStatsSql = "SELECT * FROM STATISTICS";
        try {
            return jdbcTemplate.query(findAllStatsSql, mapper);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public void saveAll(List<Stats> statsList) {
        String saveStatsSql = "INSERT INTO STATISTICS (ID, RECOMMENDATION_ID, COUNT) VALUES (?, ?, ?)";

        this.clearStatistics();

        jdbcTemplate.batchUpdate(saveStatsSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Stats stats = statsList.get(i);
                ps.setObject(1, UUID.randomUUID());
                ps.setObject(2, stats.getRecommendation().getId());
                ps.setInt(3, stats.getCount());
            }

            @Override
            public int getBatchSize() {
                return statsList.size();
            }
        });
    }

    public void clearStatistics() {
        String clearTableStatisticsSql = "TRUNCATE TABLE STATISTICS";
        jdbcTemplate.update(clearTableStatisticsSql);
    }
}

