package SearchBot.messageListener;

import java.util.function.BiPredicate;

import JDABotFramework.global.config.BotGlobalConfig;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Listener that flags messages as a certain type based on heuristics stuffs
 * @author Allen
 *
 */
public class MessageFlagger implements BiPredicate<MessageReceivedEvent, BotGlobalConfig> {
	
	@Override
	public boolean test(MessageReceivedEvent t, BotGlobalConfig u) {
		
		//add Reaction to Reaction Controller Stuffs
		return false;
	}

}
