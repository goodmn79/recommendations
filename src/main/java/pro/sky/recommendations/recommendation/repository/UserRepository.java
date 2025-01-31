package pro.sky.recommendations.recommendation.repository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendations.recommendation.mapper.row_mapper.UserRowMapper;
import pro.sky.recommendations.recommendation.model.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с таблицей USERS в базе данных.
 * <p>
 * Этот класс предоставляет методы для выполнения операций с пользователями, таких как валидация существования пользователя и поиск пользователей по имени.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 0.0.1-SNAPSHOT
 */
@Repository
public class UserRepository {
    private final JdbcTemplate transactionJdbcTemplate;

    private final UserRowMapper userRowMapper;

    private final Logger log = LoggerFactory.getLogger(UserRepository.class);

    /**
     * Конструктор для инициализации репозитория.
     *
     * @param transactionJdbcTemplate объект {@link JdbcTemplate}, используемый для работы с базой данных.
     * @param userRowMapper           объект {@link UserRowMapper}, который используется для преобразования строк результата запроса в объекты типа {@link User}.
     */
    public UserRepository(@Qualifier("transactionJdbcTemplate") JdbcTemplate transactionJdbcTemplate,
                          UserRowMapper userRowMapper) {
        this.transactionJdbcTemplate = transactionJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    /**
     * Проверка существования пользователя по его идентификатору.
     * <br>Этот метод выполняет SQL-запрос для проверки, существует ли пользователь с данным идентификатором.
     *
     * @param id идентификатор пользователя, который проверяется.
     * @return {@code true}, если пользователь существует, иначе {@code false}.
     */
    public boolean userIsExists(UUID id) {
        log.debug("Валидация пользователя по идентификатору '{}'", id);

        String userByIdIsExistsSql = "SELECT EXISTS (SELECT 1 FROM USERS u WHERE u.ID = ?) AS user_is_exist";

        boolean userIsExists = Boolean.TRUE.equals(transactionJdbcTemplate.queryForObject(userByIdIsExistsSql, Boolean.class, id));

        log.debug("Валидация пользователя завершена с результатом: '{}'", userIsExists);
        return userIsExists;
    }

    /**
     * Получение списка пользователей по ключевому слову в имени.
     * <br>Этот метод выполняет SQL-запрос для поиска пользователей, чье имя (первая часть имени) соответствует ключу.
     *
     * @param key строка, содержащая ключевое слово для поиска пользователей.
     * @return список пользователей, чьи имена соответствуют ключу.
     */
    public List<User> findUsersByNameKey(String key) {
        log.debug("Получение списка пользователей по ключу = '{}'", StringUtils.substringBefore(key, "%"));

        String findUserByKeySql = "SELECT * FROM USERS u WHERE LOWER(u.FIRST_NAME) LIKE lower(?)";

        try {
            List<User> users = transactionJdbcTemplate.query(findUserByKeySql, userRowMapper, key);
            log.debug("Список пользователей получен. Количество пользователей: '{}'", users.size());
            return users;
        } catch (Exception e) {
            log.debug(e.getMessage());
            return Collections.emptyList();
        }
    }
}
