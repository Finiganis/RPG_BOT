package mainBot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mainBot.exception.CommandParamsException;
import mainBot.utils.RandomFactory;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollCommand {
    private static final String ROLL_REGEX = "([0-9]+)D([0-9]+)";
    private static final int MAX_ROLL_AUTHORIZED = 10;
    private static final int MAX_THROW_AUTHORIZED = 5;
    private String[] params;

    public StringJoiner execute() throws CommandParamsException {
        StringJoiner sj = new StringJoiner("\n");
        if (params.length > MAX_THROW_AUTHORIZED + 1) return sj.add("Too many throws");
        String[] result = new String[params.length - 1];
        for (int i = 1; i < params.length; i++) {
            StringBuilder sb = new StringBuilder("[");

            Pattern pattern = Pattern.compile(ROLL_REGEX);
            Matcher matcher = pattern.matcher(params[i]);
            if (matcher.matches()) {
                int nbRoll = Integer.parseInt(matcher.group(1));
                int maxRoll = Integer.parseInt(matcher.group(2));

                if (nbRoll > MAX_ROLL_AUTHORIZED) {
                    sj.add("Too many rolls");
                    continue;
                }

                for (int nb = 0; nb < nbRoll; nb++) {
                    int roll = RandomFactory.getARoll() % maxRoll + 1;
                    sb.append(String.format("%d,", roll));
                }

                if (nbRoll != 0) sb.deleteCharAt(sb.length() - 1);
            } else {
                throw new CommandParamsException("Commande de lancer de dé non comprise, merci de suivre l'expression régulière " + ROLL_REGEX);
            }

            sj.add(sb.append("]").toString());
        }
        return sj;
    }
}
