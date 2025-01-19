/*
Файл репозитория для получения данных из таблицы USERS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.mapper.row_mapper.UserRowMapper;
import pro.sky.recommendations.model.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate transactionJdbcTemplate;

    private final UserRowMapper userRowMapper;

    Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate, UserRowMapper userRowMapper) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    // Валидация пользователя по его идентификатору
    public boolean userIsExists(UUID id) {
        log.debug("Validating user by id='{}'", id);

        String userByIdIsExistsSql = "SELECT EXISTS (SELECT 1 FROM USERS u WHERE u.ID = ?) AS user_is_exist";
        boolean userIsExists = Boolean.TRUE.equals(transactionJdbcTemplate.queryForObject(userByIdIsExistsSql, Boolean.class, id));

        log.debug("User validation: '{}'", userIsExists);
        return userIsExists;
    }

    // Получение пользователя по его логину
    public List<User> findUsersByNameKey(String key) {
        log.debug("Fetching user by key='{}'", StringUtils.substringBefore(key, "%"));

        String findUserByKeySql = "SELECT * FROM USERS u WHERE LOWER(u.FIRST_NAME) LIKE lower(?)";

        try {
            List<User> users = transactionJdbcTemplate.query(findUserByKeySql, userRowMapper, key);
            log.debug("Fetched user:'{}'", users);
            return users;
        } catch (Exception e) {
            log.debug(e.getMessage());
            return Collections.emptyList();
        }
    }
}
