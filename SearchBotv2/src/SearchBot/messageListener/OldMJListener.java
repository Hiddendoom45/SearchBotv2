package SearchBot.messageListener;

import java.sql.SQLException;
import java.util.function.BiPredicate;

import JDABotFramework.global.config.BotGlobalConfig;
import SearchBot.data.ReactionTable;
import SearchBot.react.MJFlag;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class OldMJListener implements BiPredicate<MessageReactionAddEvent, BotGlobalConfig> {
	private ReactionTable table;
	private MessageFlagger flagger;
	public OldMJListener(MessageFlagger flagger){
		this.table=flagger.table;
		this.flagger=flagger;
	}
	@Override
	public boolean test(MessageReactionAddEvent t, BotGlobalConfig u) {
		if (MJFlag.validEmote(t.getReactionEmote().getEmote())){
			try {
				MJFlag flag = table.pollMJFlag(t.getMessageIdLong());
				if(!(flag==null)){
					flagger.addFlag(t.getChannel().getMessageById(t.getMessageIdLong()).complete(), flag);
					return true;
				}
			} catch (SQLException e) {
				
			}
		}
		return false;
	}

}
