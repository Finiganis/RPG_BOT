package mainBot.mode.aventure;

import mainBot.commands.RollCommand;
import mainBot.exception.CommandParamsException;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandsHandlerAventure {
    public static void handle(MessageReceivedEvent event, MessageChannel channel, String[] commandParams) {
        final String mention = event.getAuthor().getAsMention() + " ";
        try {
            switch (commandParams[0]) {
                case "roll100":
                    channel.sendMessage(mention + " " + new RollCommand("1 1D100".split(" ")).execute()).queue();
                    break;
                case "roll10":
                    channel.sendMessage(mention + " " + new RollCommand("1 1D10".split(" ")).execute()).queue();
                    break;
                case "roll8":
                    channel.sendMessage(mention + " " + new RollCommand("1 1D8".split(" ")).execute()).queue();
                    break;
                case "roll6":
                    channel.sendMessage(mention + " " + new RollCommand("1 1D6".split(" ")).execute()).queue();
                    break;
                case "roll4":
                    channel.sendMessage(mention + " " + new RollCommand("1 1D4".split(" ")).execute()).queue();
                    break;
                case "roll2":
                    channel.sendMessage(mention + " " + new RollCommand("1 1D2".split(" ")).execute()).queue();
                    break;
                default:
                    channel.sendMessage(mention + "Commande " + commandParams[0] + " inconnue, pour accéder à la liste des commandes disponibles, exécuter \"!rpg commands\"").queue();
                    break;
            }
        } catch (CommandParamsException e) {
            channel.sendMessage(e.getMessage()).queue();
        }
    }
}
