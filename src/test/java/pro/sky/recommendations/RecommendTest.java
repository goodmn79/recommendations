package pro.sky.recommendations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.recommendation.dto.RecommendationData;
import pro.sky.recommendations.recommendation.model.User;
import pro.sky.recommendations.recommendation.service.UserService;
import pro.sky.recommendations.tg_bot.command.Recommend;
import pro.sky.recommendations.user_recommendation.dto.UserRecommendation;
import pro.sky.recommendations.user_recommendation.service.UserRecommendationService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRecommendationService userRecommendationService;

    @Mock
    private UserRecommendation userRecommendation;

    @InjectMocks
    private Recommend recommend;

    //Тестирование метода respond, который вызывает метод recommend
    @Test
    void shouldReturnRecommendationsWhenUserExistsAndHasRecommendations() {
        String text = "/recommend Иван Иванов";
        UUID userId = UUID.randomUUID();

        User user = new User().setId(userId).setFirstName("Иван").setLastName("Иванов");
        List<RecommendationData> recommendations = List.of(
                new RecommendationData().setProductName("Продукт 1").setProductText("Описание 1").setId(UUID.randomUUID()),
                new RecommendationData().setProductName("Продукт 2").setProductText("Описание 2").setId(UUID.randomUUID()));

        when(userService.getUserByNameKey(anyString())).thenReturn(List.of(user)); // Возвращаем пользователя
        when(userRecommendationService.getUserRecommendations(userId)).thenReturn(userRecommendation);
        when(userRecommendation.getRecommendations()).thenReturn(recommendations);

        String result = recommend.respond(text);

        assertThat(result)
                .contains("Здравствуйте Иван Иванов")
                .contains("Новые продукты для Вас:")
                .contains("Продукт 1")
                .contains("Продукт 2");
        verify(userService).getUserByNameKey("Иван%");
        verify(userRecommendationService).getUserRecommendations(userId);
        verify(userRecommendation).getRecommendations();
    }

    @Test
    void shouldReturnUserNotFoundWhenUserDoesNotExist() {
        String text = "/recommend Иван Иванов";

        when(userService.getUserByNameKey(anyString())).thenReturn(Collections.emptyList());

        String result = recommend.respond(text);

        assertThat(result).isEqualTo("Пользователь Иван Иванов не найден");
        verify(userService).getUserByNameKey("Иван%");
    }

    @Test
    void shouldReturnNoRecommendationsWhenUserExistsButNoRecommendations() {
        String text = "/recommend Иван Иванов";
        UUID userId = UUID.randomUUID();

        User user = new User().setId(userId).setFirstName("Иван").setLastName("Иванов");
        List<RecommendationData> recommendations = Collections.emptyList();

        when(userService.getUserByNameKey(anyString())).thenReturn(List.of(user));
        when(userRecommendationService.getUserRecommendations(userId)).thenReturn(userRecommendation);
        when(userRecommendation.getRecommendations()).thenReturn(recommendations);

        String result = recommend.respond(text);

        assertThat(result).contains("Здравствуйте Иван Иванов").contains("К сожалению, в настоящий момент для Вас нет подходящих продуктов");
        verify(userService).getUserByNameKey("Иван%");
        verify(userRecommendationService).getUserRecommendations(userId);
        verify(userRecommendation).getRecommendations();
    }

    @Test
    void shouldReturnIncorrectDataWhenInputIsInvalid() {
        String text = "/recommend ";

        String result = recommend.respond(text);

        assertThat(result).isEqualTo("Проверьте корректность введенных данных и повторите попытку");
    }

    //Тестирование метода extractFullName
    @Test
    void shouldExtractFullNameCorrectly() {
        String text = "/recommend Иван Иванов";
        String result = recommend.extractFullName(text);
        assertThat(result).isEqualTo("Иван Иванов");
    }

    //Тестирование сценариев метода getUserId
    @Test
    void shouldReturnUserIdWhenUserExists() {
        String fullName = "Иван Иванов";
        UUID userId = UUID.randomUUID();

        when(userService.getUserByNameKey(anyString())).thenReturn(List.of(new User().setId(userId).setFirstName("Иван").setLastName("Иванов")));

        Optional<UUID> result = recommend.getUserId(fullName);

        assertThat(result).isPresent().contains(userId);
        verify(userService).getUserByNameKey("Иван%");
    }

    @Test
    void shouldReturnEmptyWhenMultipleUsersFound() {
        String fullName = "Иван Иванов";
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        when(userService.getUserByNameKey(anyString())).thenReturn(List.of(
                new User().setId(userId1).setFirstName("Иван").setLastName("Иванов"),
                new User().setId(userId2).setFirstName("Иван").setLastName("Иванов")));

        Optional<UUID> result = recommend.getUserId(fullName);

        assertThat(result).isEmpty();
        verify(userService).getUserByNameKey("Иван%");
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        String fullName = "Неизвестный Пользователь";

        when(userService.getUserByNameKey(anyString())).thenReturn(Collections.emptyList());

        Optional<UUID> result = recommend.getUserId(fullName);

        assertThat(result).isEmpty();
        verify(userService).getUserByNameKey("Неизвестный%");
    }

    //Тестирование сценариев метода recommendationsTextBuilder
    @Test
    void shouldBuildRecommendationsTextCorrectly() {
        List<RecommendationData> recommendations = List.of(
                new RecommendationData().setProductName("Продукт 1").setProductText("Описание 1"),
                new RecommendationData().setProductName("Продукт 2").setProductText("Описание 2"));

        String result = recommend.recommendationsTextBuilder(recommendations);

        assertThat(result).contains("Продукт 1").contains("Описание 1");
        assertThat(result).contains("Продукт 2").contains("Описание 2");
    }

    @Test
    void shouldReturnEmptyStringWhenNoRecommendations() {
        List<RecommendationData> recommendations = Collections.emptyList();

        String result = recommend.recommendationsTextBuilder(recommendations);

        assertThat(result).isEmpty();
    }
}
