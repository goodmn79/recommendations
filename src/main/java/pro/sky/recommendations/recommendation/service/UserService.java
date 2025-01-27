package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.model.User;
import pro.sky.recommendations.recommendation.repository.UserRepository;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с пользователями.
 * Этот класс предоставляет методы для валидации пользователя по его идентификатору и для поиска пользователей по ключевому слову в имени.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Валидация существования пользователя по его идентификатору.
     * Метод проверяет, существует ли пользователь с данным идентификатором в базе данных.
     *
     * @param userId идентификатор пользователя, который требуется проверить.
     * @return {@code true}, если пользователь существует, иначе {@code false}.
     */
    public boolean userExists(UUID userId) {
        log.warn("Валидация пользователя по идентификатору...");

        boolean exist = userRepository.userIsExists(userId);
        if (!exist) {
            log.error("Неудачная валидация, пользователь не существует!");
        } else {
            log.info("Валидация пользователя успешно завершена.");
        }
        return exist;
    }

    /**
     * Получение списка пользователей по ключевому слову в имени.
     * Метод находит пользователей, чьи имена содержат заданное ключевое слово.
     *
     * @param NameKey ключевое слово для поиска пользователей.
     * @return список пользователей, соответствующих запросу.
     */
    public List<User> getUserByNameKey(String NameKey) {
        String keyword = StringUtils.substringBefore(NameKey, "%");
        log.warn("Получение списка пользователей с именем '{}'...", keyword);

        List<User> users = userRepository.findUsersByNameKey(NameKey);

        if (users.isEmpty()) {
            log.error("Пользователи не найдены!");
            return users;
        }

        log.info("Список пользователей с именем '{}' успешно получен.", keyword);
        return users;
    }
}
