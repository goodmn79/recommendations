/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tg_bot.command;

@FunctionalInterface
public interface Command {
    String respond(String text);
}
