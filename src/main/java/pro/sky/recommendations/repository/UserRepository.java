package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public class UserRepository {
    public static final String USER_BY_ID_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM USERS WHERE ID = ?)AS user_is_exist";

    private final JdbcTemplate transactionJdbcTemplate;

    Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    public boolean userIsExists(UUID id) {
        log.info("Invoke method 'UserRepository: userIsExists'");

        try {
            return Boolean.TRUE.equals(transactionJdbcTemplate.queryForObject(USER_BY_ID_IS_EXISTS, Boolean.class, id));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
