package SearchBot.messageListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.BiPredicate;

import JDABotFramework.global.config.BotGlobalConfig;
import JDABotFramework.react.ReactionController;
import SearchBot.data.ReactionTable;
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
	private final ReactionTable table = new ReactionTable();
	private final ArrayList<MJFlag> flags = new ArrayList<MJFlag>();
	public MessageFlagger(ReactionController control){
		this.control=control;
	}
	
	@Override
	public boolean test(MessageReceivedEvent t, BotGlobalConfig u) {
		if(t.getMessage().getContent().contains("mom joke")){
			addFlag(t.getMessage());
			return true;
		}
		//add Reaction to Reaction Controller Stuffs
		return false;
	}
	
	public void save(){
		for(MJFlag mjf : flags){
			try {
				table.saveMJFlag(mjf);
			} catch (SQLException e) {}
		}
	}
	
	public void addFlag(Message msg){
		MJFlag mjf = new MJFlag(msg.getIdLong());
		control.addReaction(msg, mjf);
		flags.add(mjf);
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
