package pro.sky.recommendations.tg_bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sky.recommendations.tg_bot.command.Command;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Конфигурация для регистрации команд бота.
 * <p>
 * Этот класс настраивает и регистрирует все доступные команды бота, используя {@link Command}.
 * Все команды собираются в {@link Map}, где ключом является имя команды, а значением — объект команды.
 * </p>
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class CommandConfiguration {
    private final Map<String, Command> commands;

    /**
     * Создаёт и возвращает карту команд, где ключом является имя команды, а значением — объект команды.
     *
     * @return карта с командами бота
     */
    @Bean
    public Map<String, Command> commands() {
        return commands
                .values()
                .stream()
                .collect(Collectors.toMap(this::commandName, command -> command));
    }

    /**
     * Получает имя команды на основе её простого имени (без пакета).
     *
     * @param command команда
     * @return строка, представляющая имя команды (в нижнем регистре)
     */
    private String commandName(Command command) {
        return command.getClass().getSimpleName().toLowerCase();
    }
}
