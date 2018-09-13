package SearchBot.react;

import JDABotFramework.react.Reaction;
import SearchBot.global.record.EmoteValues;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class MJFlag implements Reaction {

	@Override
	public boolean called(MessageReactionAddEvent event, ReactionEmote react) {
		return validEmote(react.getEmote());//check that vaoid emote
	}
	private boolean validEmote(Emote emote){
		for(EmoteValues e:EmoteValues.values()){
			if(e.name.equals(emote.getName())&&(e.id==emote.getIdLong())){
				return true;
			}
		}
		return false;
	}
	@Override
	public Message action(MessageReactionAddEvent event, ReactionEmote react, Message msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executed(MessageReactionAddEvent event) {}

	@Override
	public Long getTimeout() {
		return Long.MAX_VALUE;
	}

}
