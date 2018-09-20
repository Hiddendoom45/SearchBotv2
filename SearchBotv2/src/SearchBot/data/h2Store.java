package SearchBot.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import JDABotFramework.storage.StorageInt;

/**
 * Effectively a key-value database using a relational database backend
 * @author Allen
 *
 */
public class h2Store implements StorageInt {
	//create necessary tables
	public void setup(){
		Connection conn;
		try{
			//Generic create the table, contains a key and value
			conn = h2Connector.getConnectionPool().getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS K_VSTORE (KEY VARCHAR UNIQUE,VALUE VARCHAR)");
		}catch(SQLException e){
			
		}
	}
	@Override
	public String getString(String key) {
		try (Connection conn = h2Connector.getConnectionPool().getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT (VALUE) FROM K_VSTORE WHERE KEY=?");
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				String s = rs.getString("VALUE");
				ps.close();
				return s;
			}
		} catch (SQLException e) {
			
		}
		return "";
	}

	@Override
	public void setString(String key, String value) {
		try (Connection conn = h2Connector.getConnectionPool().getConnection()){
			PreparedStatement ps = conn.prepareStatement("MERGE INTO K_VSTORE (KEY,VALUE) KEY(KEY) VALUES (?,?)");
			ps.setString(1, key);
			ps.setString(2, value);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {

		}
	}

	@Override
	public Optional<Long> getLong(String key) {
		try (Connection conn = h2Connector.getConnectionPool().getConnection()){
			PreparedStatement ps = conn.prepareStatement("SELECT VALUE FROM K_VSTORE WHERE KEY=?");
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();
			//get from result set as string which is type of column then try to parse to long
			Optional<Long> l = Optional.of(Long.parseLong(rs.getString("VALUE")));
			ps.close();
			return l;
		}catch(SQLException|NumberFormatException e){
			
		}
		return Optional.empty();
	}

	@Override
	public void setLong(String key, long value) {
		try (Connection conn = h2Connector.getConnectionPool().getConnection();){
			PreparedStatement ps = conn.prepareStatement("MERGE INTO K_VSTORE KEY(KEY) VALUES (?,?)");
			ps.setString(1, key);
			ps.setString(2, ""+value);
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e){
			
		}
	}

	@Override
	public void unsetKey(String key) {
		try (Connection conn = h2Connector.getConnectionPool().getConnection()){
			PreparedStatement ps = conn.prepareStatement("DELETE FROM K_VSTORE WHERE KEY = ?");
			ps.setString(1, key);
			ps.executeUpdate();
		}catch(SQLException e){
			
		}
	}

	@Override
	public void push() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pull() {
		// TODO Auto-generated method stub

	}

}
