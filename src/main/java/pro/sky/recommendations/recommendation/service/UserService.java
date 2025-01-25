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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(UserService.class);

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

    public List<User> getUserByNameKey(String NameKey) {
        String keyword = StringUtils.substringBefore(NameKey, "%");
        log.warn("Получение списка пользователей с именем '{}'...", keyword);

        List<User> users = userRepository.findUsersByNameKey(NameKey);

        if (users.isEmpty()) {
            log.error("Пользователи не найдены!");
            return users;
        }

        log.info("Список прльзователей с именем '{}' успешно получен.", keyword);
        return users;
    }
}
