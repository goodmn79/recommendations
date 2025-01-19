/*
Файл репозитория для получения данных из таблицы USERS, базы данных transaction.mv.db
Powered by ©AYE.team
 */

package pro.sky.recommendations.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public class UserRepository {
    public static final String USER_BY_ID_IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM USERS WHERE ID = ?)AS user_is_exist";

    private final JdbcTemplate transactionJdbcTemplate;

    Logger log = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
    }

    // Валидация пользователя по его идентификатору
    public boolean userIsExists(UUID id) {
        log.debug("Validating user by id='{}'", id);

        boolean userIsExists = Boolean.TRUE.equals(transactionJdbcTemplate.queryForObject(USER_BY_ID_IS_EXISTS, Boolean.class, id));

        log.debug("User validation: '{}'", userIsExists);
        return userIsExists;
    }

    // Метод для получения ID пользователя по фамилии и имени
    public Optional<UUID> getUserId(String lastName, String firstName) {
        String getIdByNameSql = "SELECT id FROM users WHERE lastName = ? AND firstName = ?";
        try {
            // Получаем список UUID пользователей с одинаковыми фамилией и именем
            List<UUID> userIds = transactionJdbcTemplate.query(getIdByNameSql,
                    new Object[]{lastName, firstName},
                    (rs, rowNum) -> UUID.fromString(rs.getString("id"))
            );

            // Проверяем количество найденных пользователей
            if (userIds.isEmpty()) {
                log.error("User not found");
                throw new UserNotFoundException();
            }

            if (userIds.size() > 1) {
                log.error("More users found");
                throw new UserNotFoundException();
            }

            // Если найден один пользователь, возвращаем его ID
            return Optional.of(userIds.get(0));

        } catch (Exception e) {
            log.error("Database error", e);
            return Optional.empty();
        }
    }
}
