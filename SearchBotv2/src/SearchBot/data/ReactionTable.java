package SearchBot.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

import SearchBot.global.record.EmoteValues;
import SearchBot.react.MJFlag;

/**
 * Table holding MJFlag instances
 * @author Allen
 *
 */
public class ReactionTable {
	public ReactionTable(){
		try {
			createTable();
		} catch (SQLException e) {}
	}
	private void createTable() throws SQLException{
		Connection conn = h2Connector.getConnectionPool().getConnection();
		String s = "";
		for(EmoteValues e:EmoteValues.values()){
			s+=", "+e.textName+" INT";
		}
		conn.createStatement().execute("CREATE TABLE MJREACTION IF NOT EXISTS (ID BIGINT PRIMARY KEY"+s+" )");
	}
	public void saveMJFlag(MJFlag f) throws SQLException{
		Connection conn = h2Connector.getConnectionPool().getConnection();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO MJREACTION (ID,"+
				Arrays.stream(EmoteValues.values()).map(e -> e.emoteText).collect(Collectors.joining(","))+") VALUES(?,"+
				Arrays.stream(EmoteValues.values()).map(e -> "?").collect(Collectors.joining(","))+")");
		f.prepareStatement(ps).executeUpdate();
	}
	/**
	 * Get an old flag from the table by messageID
	 */
	public void pollMJFlag(){
		
	}
}
