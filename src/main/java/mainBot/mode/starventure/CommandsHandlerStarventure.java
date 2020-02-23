package mainBot.mode.starventure;

import mainBot.exception.CommandParamsException;
import mainBot.mode.starventure.commands.DestinRollCommand;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandsHandlerStarventure {
    public static void handle(MessageReceivedEvent event, MessageChannel channel, String[] commandParams) {
        final String mention = event.getAuthor().getAsMention() + " ";
        try {
            switch (commandParams[0]) {
                case "destinroll":
                    channel.sendMessage(new DestinRollCommand(commandParams).execute()).queue();
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
