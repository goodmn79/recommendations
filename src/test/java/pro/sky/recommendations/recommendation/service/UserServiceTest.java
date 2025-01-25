package pro.sky.recommendations.recommendation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.model.User;
import pro.sky.recommendations.recommendation.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private UUID userId;
    private String nameKey;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        nameKey = "Anna";
    }

    @Test
    void userExists_whenUserIsExist_shouldReturnTrue() {
        when(userRepository.userIsExists(userId)).thenReturn(true);

        boolean actual = userService.userExists(userId);

        verify(userRepository).userIsExists(userId);

        assertThat(actual).isTrue();
    }

    @Test
    void userExists_whenUserNotExist_shouldReturnFalse() {
        when(userRepository.userIsExists(userId)).thenReturn(false);

        boolean actual = userService.userExists(userId);

        verify(userRepository).userIsExists(userId);

        assertThat(actual).isFalse();
    }

    @Test
    void getUserByNameKey_whenUsersIsExist_shouldReturnUserList() {
        User firstUser = new User()
                .setFirstName("Anna Maria");
        User secondUser = new User()
                .setFirstName("Anna");
        when(userRepository.findUsersByNameKey(nameKey)).thenReturn(List.of(firstUser, secondUser));

        List<User> actual = userService.getUserByNameKey(nameKey);

        verify(userRepository).findUsersByNameKey(nameKey);

        assertThat(actual).isNotNull();
        assertThat(actual).containsExactly(firstUser, secondUser);
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getFirstName()).contains(nameKey);
        assertThat(actual.get(1).getFirstName()).contains(nameKey);
    }

    @Test
    void getUserByNameKey_whenUsersNotExist_shouldReturnEmptyList() {
        when(userRepository.findUsersByNameKey(nameKey)).thenReturn(Collections.emptyList());

        List<User> actual = userService.getUserByNameKey(nameKey);

        verify(userRepository).findUsersByNameKey(nameKey);

        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();
    }
}