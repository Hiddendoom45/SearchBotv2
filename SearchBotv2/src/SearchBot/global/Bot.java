package SearchBot.global;

import JDABotFramework.launcher.DiscordBot;
import SearchBot.messageListener.MessageFlagger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Bot extends DiscordBot {
	//TODO add something to store stuff from flagger
	
	public Bot(BotInit init) {
		super(init);
		//add flagger stuffs
		main.getEventChain(MessageReceivedEvent.class).addListener("Flagger", new MessageFlagger(react));
	}

	@Override
	protected void setup() {
		
	}

	@Override
	public String help(MessageReceivedEvent event) {
		String s = "__***Help List***__\n"
				+ "Use "+config.getPrefix(event.getGuild())+"help [command] "
				+ "to get more info on a specific command, i.e.: "+config.getPrefix(event.getGuild())+"help ping\n\n";
		return s;
	}

	@Override
	public String modHelp(MessageReceivedEvent event) {
		return null;
	}

}
