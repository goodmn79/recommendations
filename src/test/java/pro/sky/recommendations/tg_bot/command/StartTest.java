package pro.sky.recommendations.tg_bot.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class StartTest {
    @InjectMocks
    private Start start;

    @Test
    void shouldReturnCorrectResponseWhenCommandIsStart() {
        String text = "/start";

        String result = start.respond(text);

        assertThat(result)
                .isEqualTo("Для получения информации о доступных Вам новых продуктах введите:\n/recommend <Имя Фамилия>");
    }
}

