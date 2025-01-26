package pro.sky.recommendations.tg_bot.service;


import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendations.tg_bot.command.Command;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private final String replyText = "Reply text";


    @Test
    void testGetUserRecommendations_withStartCommand() {
        String start = "start";
        String command = command(start);
        when(message.text()).thenReturn(command);
        when(commands.get(start)).thenReturn(startCommand);
        when(startCommand.respond(command)).thenReturn(replyText);

        String actual = botService.getUserRecommendations(message);

        assertThat(actual).isEqualTo(replyText);
        verify(startCommand).respond(command);
    }

    @Test
    void testGetUserRecommendations_withRecommendCommand() {
        String recommend = "recommend";
        String command = command(recommend);
        when(message.text()).thenReturn(command);
        when(commands.get(recommend)).thenReturn(recommendCommand);
        when(recommendCommand.respond(command)).thenReturn(replyText);

        String actual = botService.getUserRecommendations(message);

        assertThat(actual).isEqualTo(replyText);
        verify(recommendCommand).respond(command);
    }

    @Test
    void testGetUserRecommendations_whenTextIsBlank_shouldReturnIncorrectDataMessage() {
        when(message.text()).thenReturn("");

        String result = botService.getUserRecommendations(message);

        assertThat(result).isEqualTo(BotService.INCORRECT_DATA);
    }

    @Test
    void testGetUserRecommendations_whenUnknownCommand_shouldReturnIncorrectDataMessage() {
        String invalidCommand = "unknown command";
        when(message.text()).thenReturn(invalidCommand);

        String result = botService.getUserRecommendations(message);

        assertThat(result).isEqualTo(BotService.INCORRECT_DATA);
    }

    private String command(String commandString) {
        return "/" + commandString;
    }
}


