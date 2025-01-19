package pro.sky.recommendations.tgBot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.recommendations.tgBot.command.Command;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CommandConfiguration {
    private final Map<String, Command> commands;

    @Bean
    public Map<String, Command> commands() {
        return commands
                .values()
                .stream()
                .collect(Collectors.toMap(this::commandName, command -> command));
    }

    private String commandName(Command command) {
        return command.getClass().getSimpleName().toLowerCase();
    }
}
