package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.row_mapper.UserRowMapper;
import pro.sky.recommendations.model.User;

import java.util.Optional;
import java.util.UUID;

import static pro.sky.recommendations.constant.SQLQuery.FIND_USER_BY_ID;

@Repository

public class UserRepository {
    private final JdbcTemplate transactionJdbcTemplate;
    private final UserRowMapper mapper;

    Logger logger= LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate,
                          UserRowMapper mapper) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
        this.mapper = mapper;
    }

    public Optional<User> findById(UUID id) {
        try {
            User user = transactionJdbcTemplate.queryForObject(FIND_USER_BY_ID, mapper, id);
            logger.info("User found: {}", user);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            logger.info("User with id '{}' not found", id);
            return Optional.empty();
        }
    }
}
