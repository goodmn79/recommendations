package pro.sky.recommendations.tg_bot.command;

/**
 * Интерфейс для команд бота.
 * <p>
 * Каждая команда должна реализовывать метод {@link #respond(String)} для обработки входных данных и возвращения ответа.
 * </p>
 * Используется в контексте обработки команд в телеграм-боте.
 *
 * @author Powered by ©AYE.team
 * @version 1.0
 */

@FunctionalInterface
public interface Command {

    /**
     * Обрабатывает входное сообщение и генерирует ответ.
     *
     * @param text текст, переданный с командой
     * @return строка-ответ, которую бот отправит пользователю
     */
    String respond(String text);
}
