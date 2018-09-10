package SearchBot.global;

import JDABotFramework.commands.Ping;
import JDABotFramework.launcher.DiscordBot;
import SearchBot.global.record.Modules;
import SearchBot.messageListener.MessageFlagger;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Bot extends DiscordBot {
	//TODO add something to store stuff from flagger
	
	
	public Bot(BotInit init) {
		super(init);
		//add flagger stuffs
		main.getEventChain(MessageReceivedEvent.class).addListener("Flagger", new MessageFlagger());
		//add Core commands
		cmd.addCommand("ping", new Ping(config), Modules.Core.toString());
	}

	@Override
	protected void setup() {
		
	}

	@Override
	protected String help() {
		return null;
	}

	@Override
	protected String modHelp() {
		return null;
	}

}
