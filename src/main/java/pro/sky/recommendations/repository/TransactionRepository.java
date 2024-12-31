package pro.sky.recommendations.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.row_mapper.TransactionRowMapper;
import pro.sky.recommendations.model.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static pro.sky.recommendations.constant.SQLQuery.FIND_ALL_TRANSACTION_BY_USER_ID;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper mapper;

    public TransactionRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate jdbcTemplate,
                                 TransactionRowMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public boolean isCompliance(String query, UUID userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, userId));
    }

    public List<Transaction> findAllTransactionByUserId(UUID id) {
        try {
            return jdbcTemplate.queryForStream(FIND_ALL_TRANSACTION_BY_USER_ID, mapper, id)
                    .toList();
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
}
