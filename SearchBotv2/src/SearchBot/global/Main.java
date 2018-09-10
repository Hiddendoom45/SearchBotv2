package SearchBot.global;

import javax.security.auth.login.LoginException;

import JDABotFramework.launcher.BotBuilder;
import SearchBot.global.record.Secrets;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Main {

	public static void main(String[] args) throws LoginException, IllegalArgumentException, RateLimitedException, InterruptedException {
		BotBuilder b = new BotBuilder(Secrets.token);
		Bot d = new Bot(b.buildBlocking());
		d.startup();
	}

}
