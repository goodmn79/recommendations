package pro.sky.recommendations.tg_bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.recommendations.tg_bot.command.Command;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Конфигурация для создания карты команд.
 */
@Configuration
@RequiredArgsConstructor
public class CommandConfiguration {

    private final Map<String, Command> commands;

    /**
     * Создание карты команд.
     * @return {@link Map} с ключами в виде имен команд и значениями в виде объектов {@link Command}.
     */
    @Bean
    public Map<String, Command> commands() {
        return commands
                .values()
                .stream()
                .collect(Collectors.toMap(this::commandName, command -> command));
    }

    /**
     * Получение имени команды, использующее название класса команды в нижнем регистре.
     *
     * @param command команда, для которой нужно получить имя.
     * @return строка, представляющая имя команды в нижнем регистре.
     */
    private String commandName(Command command) {
        return command.getClass().getSimpleName().toLowerCase();
    }
}
