package pro.sky.recommendations.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.recommendation.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(UserService.class);

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
}
