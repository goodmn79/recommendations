package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.exception.UserNotFoundException;
import pro.sky.recommendations.repository.UserRepository;

import java.util.Optional;
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

    public UUID userId (String lastName, String firstName) {
        log.info("Validating user by name...");

        Optional<UUID> userId = userRepository.getUserId(lastName, firstName);
        if (userId.isPresent()) {
            return userId.get();
        } else {
            log.warn("User not found");
            throw new UserNotFoundException();
        }
    }

}
