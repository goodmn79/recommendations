package pro.sky.recommendations.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.TransactionMapper;
import pro.sky.recommendations.model.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static pro.sky.recommendations.repository.constant.SQLQuery.FIND_ALL_TRANSACTION_BY_USER_ID;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionMapper mapper;

    public List<Transaction> findAllTransactionByUserId(UUID id) {
        try {
            return jdbcTemplate.queryForStream(FIND_ALL_TRANSACTION_BY_USER_ID, mapper, id)
                    .toList();
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
}
