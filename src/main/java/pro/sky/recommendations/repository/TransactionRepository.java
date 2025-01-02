/*
Файл репозитория для получения данных из таблицы TRANSACTIONS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(QueryRepository.class);

    public TransactionRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Проверка соответствия требованию правила рекомендации банковского продукта
    public boolean isCompliance(String query, UUID userId) {
        log.info("Checking compliance...");

        boolean isCompliance = Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, userId));

        log.info("Compliance check result: '{}'", isCompliance);
        return isCompliance;
    }
}
