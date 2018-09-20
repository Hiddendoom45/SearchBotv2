package SearchBot.global;

import JDABotFramework.launcher.DiscordBot;
import SearchBot.data.h2Connector;
import SearchBot.messageListener.MessageFlagger;
import SearchBot.messageListener.OldMJListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class Bot extends DiscordBot {
	//TODO add something to store stuff from flagger
	private MessageFlagger flagger;
	public Bot(BotInit init) {
		super(init);
		//add flagger stuffs
		flagger = new MessageFlagger(react);
		main.getEventChain(MessageReceivedEvent.class).addListener("MJFlagger", new MessageFlagger(react));
		main.getEventChain(MessageReactionAddEvent.class).addListener("OldMJ", new OldMJListener(flagger));
	}

	@Override
	protected void setup() {
		
	}

	@Override
	public String help(MessageReceivedEvent event) {
		//Generic help header TODO add more stuff
		String s = "__***Help List***__\n"
				+ "Use "+config.getPrefix(event.getGuild())+"help [command] "
				+ "to get more info on a specific command, i.e.: "+config.getPrefix(event.getGuild())+"help ping\n\n";
		return s;
	}

	@Override
	public String modHelp(MessageReceivedEvent event) {
		return null;
	}

	@Override
	protected void shutdown() {
		flagger.save();
		h2Connector.shutdown();
	}

}
