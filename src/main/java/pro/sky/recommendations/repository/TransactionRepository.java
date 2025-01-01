/*
Файл репозитория для получения данных из таблицы TRANSACTIONS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Проверка соответствия требованию правила рекомендации банковского продукта
    public boolean isCompliance(String query, UUID userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, userId));
    }
}
