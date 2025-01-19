/**
 * @author Powered by ©AYE.team
 */

package pro.sky.recommendations.tgBot.command;

@FunctionalInterface
public interface Command {
    String respond(String text);
}
