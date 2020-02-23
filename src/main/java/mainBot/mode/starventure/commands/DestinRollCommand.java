package mainBot.mode.starventure.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mainBot.exception.CommandParamsException;
import mainBot.utils.RandomFactory;

import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor
public class DestinRollCommand {
    private static final int MAX_ROLL_AUTHORIZED = 5;
    private String[] params;

    public String execute() throws CommandParamsException {
        int nbRoll = Integer.parseInt(params[1]);
        if (nbRoll > MAX_ROLL_AUTHORIZED)
            throw new CommandParamsException("Le nombre de dés à lancer est supérieur à " + MAX_ROLL_AUTHORIZED);

        StringJoiner sj = new StringJoiner("\n");
        sj.add("Résultat du lancé :");

        for (int i = 0; i < nbRoll; i++) {
            int roll = RandomFactory.getARoll(6);
            sj.add(String.format("[%s] : %s", roll, rolltoeffect(roll)));
        }

        return sj.toString();
    }

    private String rolltoeffect(int roll) throws CommandParamsException {
        String[] tab = new String[6];
        tab[0] = "+5% réussite critique";
        tab[1] = "+20% réussite";
        tab[2] = "neutre";
        tab[3] = "conséquence";
        tab[4] = "-20% réussite";
        tab[5] = "+5% échec critique";
        if (roll < 1 || roll > 6)
            throw new CommandParamsException("Le dé passé en argument a une valeur supérieure à 6 ou inférieure à 1");
        return tab[roll - 1];
    }
}
