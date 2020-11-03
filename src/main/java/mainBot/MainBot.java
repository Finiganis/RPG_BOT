package mainBot;

import mainBot.commands.RollCommand;
import mainBot.domain.Settings;
import mainBot.exception.CommandParamsException;
import mainBot.exception.RoleException;
import mainBot.mode.aventure.CommandsHandlerAventure;
import mainBot.mode.aventure.domain.Character;
import mainBot.mode.starventure.CommandsHandlerStarventure;
import mainBot.utils.ResultRollUtil;
import mainBot.utils.Usefull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class MainBot extends ListenerAdapter {
	private ArrayList<Character> characters = new ArrayList<Character>();

	public static void main(String[] args) throws LoginException, InterruptedException {
		if (args.length < 1) {
			System.out.println("Veulliez indiquer le token du bot");
		}
		JDA jda = new JDABuilder(args[0]).addEventListeners(new MainBot()).build();
		jda.awaitReady();
		System.out.println("Finished Building JDA!");
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		//Event specific information
		User author = event.getAuthor();
		Message message = event.getMessage();
		MessageChannel channel = event.getChannel();

		if(author.isBot()) return;

		if (event.isFromType(ChannelType.TEXT))
		{
			String content = event.getMessage().getContentDisplay();
			if (content.startsWith("!rpg ")) {
				content = content.substring(5);
				String[] commandParams = content.split(" ");
				content = commandParams.length != 0 ? commandParams[0] : "";
				try {
					generalCommands(event, channel, commandParams);
				} catch (CommandParamsException | RoleException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			}
			System.out.printf("[%s][%s] %#s: %s%n", event.getGuild().getName(),
					event.getChannel().getName(), event.getAuthor(), event.getMessage().getContentDisplay());
		}
		else
		{
			System.out.printf("[PM] %#s: %s%n", event.getAuthor(), event.getMessage().getContentDisplay());
		}
	}

	private void generalCommands(MessageReceivedEvent event, MessageChannel channel, String[] commandParams) throws CommandParamsException, RoleException {
		final String mention = event.getAuthor().getAsMention() + " ";
		if (commandParams.length == 0) throw new CommandParamsException(mention + "Commande invalide, pour accéder à la liste des commandes disponibles, exécuter \"!rpg commands\"");
		Settings.modeMap.putIfAbsent(channel.getName(), Settings.Mode.NONE);
		boolean isMJ = Usefull.isMJ(event);

		switch(commandParams[0]) {
			case "baseroll" :
				RollCommand command = new RollCommand(commandParams);
				channel.sendMessage(event.getAuthor().getAsMention() + " " + ResultRollUtil.messageRoll(command)).queue();
				return;
			case "setmode" :
				try {
					Settings.modeMap.put(channel.getName(), Settings.Mode.valueOf(commandParams[1]));
					channel.sendMessage(mention + "Mode du channel " + channel.getName() + " : " + Settings.modeMap.get(channel.getName())).queue();
				} catch (IllegalArgumentException e) {
					channel.sendMessage(mention + "Mode inconnu, liste des modes existants :\n" + Settings.listeModes()).queue();
				}
				return;
			case "getmode" :
				channel.sendMessage(mention + "Mode actuel sur le channel " + channel.getName() + " : " + Settings.modeMap.get(channel.getName())).queue();
				return;
			case "saveSettings":
				channel.sendMessage(mention + Settings.saveSettings()).queue();
				return;
			default:
				break;
		}

		switch (Settings.modeMap.get(channel.getName())) {
			case AVENTURE:
				CommandsHandlerAventure.handle(event, channel, commandParams);
				break;
			case STARVENTURE:
				CommandsHandlerStarventure.handle(event, channel, commandParams);
				break;
			case NONE:
				channel.sendMessage(mention + "Commande " + commandParams[0] + " inconnue, pour accéder à la liste des commandes disponibles, exécuter \"!rpg commands\"").queue();
				break;
		}
	}

	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		Settings.loadSettings();
	}

	/*
	@Override
	public void onEvent(Event event){
		if (event instanceof PrivateMessageReceivedEvent){
			PrivateMessageReceivedEvent e = (PrivateMessageReceivedEvent) event;
			if (e.getMessage().getContentRaw().equalsIgnoreCase("ping")){
				e.getChannel().sendTyping();
				e.getChannel().sendMessage("pong").queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D100")){
				int res = (RandomFactory.getARoll()%100 + 100)%100 + 1;
				e.getChannel().sendTyping();
				if(res <  6){
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 100 : " + res + "\nCOUP CRITIQUE !!!").queue();
				} else if(res > 95){
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 100 : " + res + "\nECHEC CRITIQUE !!!").queue();
				} else {
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 100 : " + res).queue();
				}
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D6")){
				int res = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 6 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D8")){
				int res = (RandomFactory.getARoll()%8 + 8)%8 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 8 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D10")){
				int res = (RandomFactory.getARoll()%10 + 10)%10 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 10 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D12")){
				int res = (RandomFactory.getARoll()%12 + 12)%12 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 12 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("help")){
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Commandes existantes :\n-Ping\n-1D100\n-1D6\n-1D8\n-1D10\n-1D12\n-new character [name] [HP] [mana]").queue();
			}/*
			try{
				if (e.getMessage().getContentRaw().equalsIgnoreCase("save")){
					JSONArray array = new JSONArray();
					for(Characters c : characters){
						JSONObject jo = new JSONObject();
						jo.put("name", c.getName());
						jo.put("HP", c.getHP());
						jo.put("mana", c.getMana());
						array.put(jo);
					}
					System.out.println(array.toString());
					JSONSave.Save(array);
					e.getChannel().sendTyping();
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " sauvegarde effectu�e").queue();
				}
				if (e.getMessage().getContentRaw().substring(0,13).equalsIgnoreCase("new character")){
					String[] content = e.getMessage().getContentRaw().split(" ");
					characters.add(new Characters(content[2], new Integer(content[3]), new Integer(content[4])));
					e.getChannel().sendTyping();
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Personnage " + content[2] + " cr��").queue();
				}
			} catch (StringIndexOutOfBoundsException | IOException | JSONException exception){
				System.out.println("it's nothing or save/load error");
			}
		}
		if (event instanceof MessageReceivedEvent){
			MessageReceivedEvent e = (MessageReceivedEvent) event;
			if (e.getMessage().getContentRaw().equalsIgnoreCase("ping")){
				e.getChannel().sendTyping();
				e.getChannel().sendMessage("pong").queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D100")){
				int res = (RandomFactory.getARoll()%100 + 100)%100 + 1;
				e.getChannel().sendTyping();
				if(res <  6){
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 100 : " + res + "\nCOUP CRITIQUE !!!").queue();
				} else if(res > 95){
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 100 : " + res + "\nECHEC CRITIQUE !!!").queue();
				} else {
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 100 : " + res).queue();
				}
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D6")){
				int res = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 6 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("sw 1D6")){
				int roll = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 6 : " + roll + "\n Effet : " + Usefull.rolltoeffect(roll)).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("sw 2D6")){
				int roll1 = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				int roll2 = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				String s = " R�sultat des d� 6 : " + roll1 + " / " + roll2 + "\n Effets : " + Usefull.rolltoeffect(roll1) +" / " + Usefull.rolltoeffect(roll2);
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + s).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("sw 3D6")){
				int roll1 = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				int roll2 = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				int roll3 = (RandomFactory.getARoll()%6 + 6)%6 + 1;
				String s = " R�sultat des d� 6 : " + roll1 + " / " + roll2 + " / " + roll3 + "\n Effets : " + Usefull.rolltoeffect(roll1) + " / " + Usefull.rolltoeffect(roll2) + " / " + Usefull.rolltoeffect(roll3);
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + s).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D8")){
				int res = (RandomFactory.getARoll()%8 + 8)%8 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 8 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D10")){
				int res = (RandomFactory.getARoll()%10 + 10)%10 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 10 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("1D12")){
				int res = (RandomFactory.getARoll()%12 + 12)%12 + 1;
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " R�sultat du d� 12 : " + res).queue();
			}
			if (e.getMessage().getContentRaw().equalsIgnoreCase("help")){
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Commandes existantes :\n-Ping\n-1D100\n-1D6\n-1D8\n-1D10\n-1D12\n-new character [name] [HP] [mana]").queue();
			}/*
			try{
				if (e.getMessage().getContentRaw().equalsIgnoreCase("save")){
					JSONArray array = new JSONArray();
					for(Characters c : characters){
						JSONObject jo = new JSONObject();
						jo.put("name", c.getName());
						jo.put("HP", c.getHP());
						jo.put("mana", c.getMana());
						array.put(jo);
					}
					System.out.println(array.toString());
					JSONSave.Save(array);
					e.getChannel().sendTyping();
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " sauvegarde effectu�e").queue();
				}
				if (e.getMessage().getContentRaw().substring(0,13).equalsIgnoreCase("new character")){
					String[] content = e.getMessage().getContentRaw().split(" ");
					characters.add(new Characters(content[2], new Integer(content[3]), new Integer(content[4])));
					e.getChannel().sendTyping();
					e.getChannel().sendMessage(e.getAuthor().getAsMention() + " Personnage " + content[2] + " cr��").queue();
				}
			} catch (StringIndexOutOfBoundsException | IOException | JSONException exception){
				System.out.println("it's nothing or save/load error");
			}
		}
	}*/
}
