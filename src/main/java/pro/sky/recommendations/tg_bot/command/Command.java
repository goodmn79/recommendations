/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tg_bot.command;

/**
 * Интерфейс для представления команды, которую может обработать бот.
 * Является функциональным, чтобы его можно было использовать в контексте лямбда-выражений или ссылок на методы.
 */
@FunctionalInterface
public interface Command {

    String respond(String text);
}
