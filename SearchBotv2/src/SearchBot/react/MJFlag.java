package SearchBot.react;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import JDABotFramework.react.Reaction;
import SearchBot.global.record.EmoteValues;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;

/**
 * Thing that actually tracks points
 * @author Allen
 *
 */
public class MJFlag implements Reaction {
	private final HashMap<EmoteValues,Integer> reactions = new HashMap<EmoteValues,Integer>();
	private final long messageID;
	private final long userID;
	public MJFlag(long messageID,long userID){
		this.messageID=messageID;
		this.userID=userID;
	}
	/**
	 * Constructs the reaction using the current row of the resultset.
	 * Must contain column names matching {@link EmoteValues} textName field
	 * @param s
	 */
	public MJFlag(ResultSet s){
		long l;
		try {
			l = s.getLong("ID");
		} catch (SQLException e2) {
			l=-1;//generic error ID;
		}
		messageID = l;
		try{
			l = s.getLong("USER_ID");
		} catch(SQLException e3){
			l = -1;
		}
		userID=l;
		for(EmoteValues e:EmoteValues.values()){
			try {
				int i = s.getInt(e.textName);
				if(i!=0){
					reactions.put(e, i);
				}
			} catch (SQLException e1) {}
		}
	}
	
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
	
	public static boolean validEmote(Emote emote){
		for(EmoteValues e:EmoteValues.values()){
			if(e.name.equals(emote.getName())&&(e.id==emote.getIdLong())){
				return true;
			}
		}
		return false;
	}
	
	public PreparedStatement prepareStatement(PreparedStatement ps){
		int i = 1;
		try {
			ps.setLong(i, messageID);
		} catch (SQLException e2) {}
		i++;
		try{
			ps.setLong(i, userID);
		}catch(SQLException e3){}
		for(EmoteValues e:EmoteValues.values()){
			i++;
			try {
				ps.setInt(i, reactions.get(e).intValue());
			} catch (SQLException e1) {}
		}
		return ps;
	}

}
