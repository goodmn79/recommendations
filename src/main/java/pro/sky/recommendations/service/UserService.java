package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.model.User;
import pro.sky.recommendations.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(UserService.class);

    // Проверка наличия пользователя в базе данных
    public boolean userExists(UUID userId) {
        log.info("Validating user by id...");

        boolean exist = userRepository.userIsExists(userId);
        if (!exist) {
            log.warn("User validation failed");
        } else {
            log.info("User validation completed successfully");
        }
        return exist;
    }

    public List<User> getUserByNameKey(String NameKey) {
        log.info("Fetching user by NameKey='{}'", StringUtils.substringBefore(NameKey, "%"));

        List<User> users = userRepository.findUsersByNameKey(NameKey);

        if (users.isEmpty()) {
            log.warn("User not found");
            return users;
        }

        log.info("User successfully found");
        return users;
    }
}
