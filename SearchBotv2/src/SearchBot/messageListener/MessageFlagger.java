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
 * Also currently the main interface to control MJ points and such
 * @author Allen
 *
 */
public class MessageFlagger implements BiPredicate<MessageReceivedEvent, BotGlobalConfig> {
	public final ReactionController control;//reaction controller for the bot
	public final ReactionTable table = new ReactionTable();//table to persist the data
	private final ArrayList<MJFlag> flags = new ArrayList<MJFlag>();//list of new flags added since last start
	public MessageFlagger(ReactionController control){
		this.control=control;
	}
	
	/**
	 * Main flagger, flags message as a mom joke or otherwise
	 */
	@Override
	public boolean test(MessageReceivedEvent t, BotGlobalConfig u) {
		if(t.getMessage().getContent().contains("mom joke")){
			addFlag(t.getMessage());
			return true;
		}
		//add Reaction to Reaction Controller Stuffs
		return false;
	}
	/**
	 * Saves to table
	 */
	public void save(){
		for(MJFlag mjf : flags){
			try {
				table.saveMJFlag(mjf);
			} catch (SQLException e) {}
		}
	}
	/**
	 * Does everything that's needed to mark a message as a mom joke
	 * - adds reactions
	 * - adds to reaction controller
	 * - adds to internal cache of MJFlags added
	 * @param msg
	 */
	public void addFlag(Message msg){
		MJFlag mjf = new MJFlag(msg.getIdLong(),msg.getAuthor().getIdLong());
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
	/**
	 * Adds an old joke to the cached list
	 * @param msg old message
	 * @param flag old flag object
	 */
	public void addFlag(Message msg,MJFlag flag){
		control.addReaction(msg, flag);
		flags.add(flag);
	}
}
