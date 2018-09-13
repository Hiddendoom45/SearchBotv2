package SearchBot.react;

import java.util.HashMap;

import JDABotFramework.react.Reaction;
import SearchBot.global.record.EmoteValues;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;

public class MJFlag implements Reaction {
	private final HashMap<EmoteValues,Integer> reactions = new HashMap<EmoteValues,Integer>();
	
	@Override
	public boolean called(MessageReactionAddEvent event, ReactionEmote react) {
		return validEmote(react.getEmote());//check that avoid emote
	}
	
	@Override
	public Message action(MessageReactionAddEvent event, ReactionEmote react, Message msg) {
		EmoteValues e = EmoteValues.getValueFor(react.getEmote());
		if(reactions.containsKey(e)){
			reactions.put(e, reactions.get(e)+1);
		}
		else{
			reactions.put(e, 1);
		}
		return msg;
	}

	@Override
	public void executed(MessageReactionAddEvent event) {}

	@Override
	public Long getTimeout() {
		return Long.MAX_VALUE;
	}
	@Override
	public boolean called(MessageReactionRemoveEvent event, ReactionEmote react) {
		return validEmote(event.getReactionEmote().getEmote());
	}
	@Override
	public Message action(MessageReactionRemoveEvent event, ReactionEmote react, Message msg) {
		EmoteValues e = EmoteValues.getValueFor(react.getEmote());
		if(reactions.containsKey(e)){
			reactions.put(e, reactions.get(e)+1);
		}
		else{//TODO figure a better error handling thingy since this shouldn't be triggered most of the time
			reactions.put(e, -1);
		}
		return msg;
	}
	@Override
	public void executed(MessageReactionRemoveEvent event) {}
	
	private boolean validEmote(Emote emote){
		for(EmoteValues e:EmoteValues.values()){
			if(e.name.equals(emote.getName())&&(e.id==emote.getIdLong())){
				return true;
			}
		}
		return false;
	}

}
