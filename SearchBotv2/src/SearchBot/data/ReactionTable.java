package SearchBot.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	//attempt to create table if it doesn't exist in database
	public ReactionTable(){
		try {
			createTable();
		} catch (SQLException e) {}
	}
	/**
	 * Creates the table to avoid table doesn't exist errors
	 * @throws SQLException
	 */
	private void createTable() throws SQLException{
		Connection conn = h2Connector.getConnectionPool().getConnection();
		String s = "";
		//generate the columns for the emotes based on the EmoteValues enum, textName is the column friendly string
		for(EmoteValues e:EmoteValues.values()){
			s+=", "+e.textName+" INT";
		}
		conn.createStatement().execute("CREATE TABLE MJREACTION IF NOT EXISTS (ID BIGINT PRIMARY KEY"+s+" )");
	}
	/**
	 * Inserts the reaction into the table
	 * @param f
	 * @throws SQLException
	 */
	public void saveMJFlag(MJFlag f) throws SQLException{
		Connection conn = h2Connector.getConnectionPool().getConnection();
		//merge in the case of updating old ones
		PreparedStatement ps = conn.prepareStatement("MERGE INTO MJREACTION (ID, USER_ID"+
				Arrays.stream(EmoteValues.values()).map(e -> e.emoteText).collect(Collectors.joining(","))+")  KEY(ID) VALUES(?,"+
				Arrays.stream(EmoteValues.values()).map(e -> "?").collect(Collectors.joining(","))+")");
		
		f.prepareStatement(ps).executeUpdate();
	}
	/**
	 * Get an old flag from the table by messageID
	 * @throws SQLException 
	 */
	public MJFlag pollMJFlag(long id) throws SQLException{
		Connection conn = h2Connector.getConnectionPool().getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM MJREACTION WHERE ID = ?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return new MJFlag(rs);
		}
		else return null;
	}
}
