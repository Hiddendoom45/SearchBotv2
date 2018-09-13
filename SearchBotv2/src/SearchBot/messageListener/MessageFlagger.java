package SearchBot.messageListener;

import java.util.function.BiPredicate;

import JDABotFramework.global.config.BotGlobalConfig;
import JDABotFramework.react.ReactionController;
import SearchBot.global.record.EmoteValues;
import SearchBot.react.MJFlag;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Listener that flags messages as a certain type based on heuristics stuffs
 * @author Allen
 *
 */
public class MessageFlagger implements BiPredicate<MessageReceivedEvent, BotGlobalConfig> {
	private final ReactionController control;
	public MessageFlagger(ReactionController control){
		this.control=control;
	}
	@Override
	public boolean test(MessageReceivedEvent t, BotGlobalConfig u) {
		
		//add Reaction to Reaction Controller Stuffs
		return false;
	}
	
	public void addFlag(Message msg){
		control.addReaction(msg, new MJFlag());
		//Adds all the valid emotes as reactions
		for(EmoteValues e:EmoteValues.values()){
			if(e.id==0){
				msg.addReaction(e.name).complete();
			}
			else{
				msg.addReaction(msg.getJDA().getEmoteById(e.id));
			}
		}
	}
}
