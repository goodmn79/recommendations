/*
Файл репозитория для получения данных из таблицы USERS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public class UserRepository {

    private final JdbcTemplate transactionJdbcTemplate;

    Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    // Валидация пользователя по его идентификатору
    public boolean userIsExists(UUID id) {
        log.debug("Validating user by id='{}'", id);

        String userByIdIsExistsSql = "SELECT EXISTS(SELECT 1 FROM USERS WHERE ID = ?)AS user_is_exist";

        boolean userIsExists = Boolean.TRUE.equals(transactionJdbcTemplate.queryForObject(userByIdIsExistsSql, Boolean.class, id));

        log.debug("User validation: '{}'", userIsExists);
        return userIsExists;
    }
}
