package pro.sky.recommendations;


import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.sky.recommendations.tg_bot.command.Command;
import pro.sky.recommendations.tg_bot.service.BotService;


import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {

    @Mock
    private Map<String, Command> commands;

    @Mock
    private Command startCommand;
    @Mock
    private Command recommendCommand;

    @Mock
    private Message message;

    @InjectMocks
    private BotService botService;

    private String startCommandText;
    private String recommendCommandText;
    private String  unknownCommandText;

    @BeforeEach
    void SetUp() {
        startCommandText = "/start";
        recommendCommandText = "/recommend";
        unknownCommandText = "/unknown";
    }

    @Test
    void testGetUserRecommendations_withStartCommand() {
        when(message.text()).thenReturn(startCommandText);
        when(commands.get("start")).thenReturn(startCommand);

        when(startCommand.respond(startCommandText)).thenReturn("Для получения информации...");

        String result = botService.getUserRecommendations(message);

        assertThat(result).isEqualTo("Для получения информации...");
        verify(startCommand).respond(startCommandText);
    }

    @Test
    void testGetUserRecommendations_withRecommendCommand() {
        when(message.text()).thenReturn(recommendCommandText);
        when(commands.get("recommend")).thenReturn(recommendCommand);

        when(recommendCommand.respond(recommendCommandText)).thenReturn("Новые продукты для Вас");

        String result = botService.getUserRecommendations(message);

        assertThat(result).isEqualTo("Новые продукты для Вас");
        verify(recommendCommand).respond(recommendCommandText);
    }

    @Test
    void testGetUserRecommendations_withUnknownCommand() {
        when(message.text()).thenReturn(unknownCommandText);
        when(commands.get("unknown")).thenReturn(null);

        String result = botService.getUserRecommendations(message);

        assertThat(result).isEqualTo("Проверьте корректность введенных данных и повторите попытку");
        verify(commands).get("unknown");
    }

    @Test
    void testGetCommand_withStartCommand() {
        String text = "/start arg1 arg2";
        when(commands.get("start")).thenReturn(startCommand);

        Optional<Command> result = botService.getCommand(text);

        assertThat(result).hasValue(startCommand);
        verify(commands).get("start");
    }

    @Test
    void testGetCommand_withRecommendCommand() {
        String text = "/recommend arg1 arg2";
        when(commands.get("recommend")).thenReturn(recommendCommand);

        Optional<Command> result = botService.getCommand(text);

        assertThat(result).hasValue(recommendCommand);
        verify(commands).get("recommend");
    }

    @Test
    void testGetCommand_withUnknownCommand() {
        Optional<Command> result = botService.getCommand(unknownCommandText);

        assertThat(result).isNotPresent();
        verify(commands).get("unknown");
    }
}


