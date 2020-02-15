package mainBot.utils;

import mainBot.commands.RollCommand;
import mainBot.exception.CommandParamsException;

import java.util.StringJoiner;

public class ResultRollUtil {
    public static String messageRoll (RollCommand command) throws CommandParamsException {
        StringJoiner result = command.execute();
        StringJoiner sj = new StringJoiner("\n").add("Résultat des lancés :");
        sj.merge(result);
        return sj.toString();
    }
}
