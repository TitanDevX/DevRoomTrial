package me.titan.devroomtrial.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import me.titan.devroomtrial.config.MainConfig;
import me.titan.devroomtrial.core.DevRoomTrialPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

	MysqlDataSource dataSource;
	private static String TABLE = "players";
	public MysqlDataSource getDataSource(){
		MysqlDataSource s = new MysqlDataSource();


		MainConfig config = DevRoomTrialPlugin.instance.getMainConfig();
		s.setServerName(config.getDatabase_host());
		s.setPort(config.getDatabase_port());
		s.setDatabaseName(config.getDatabase_db());
		s.setUser(config.getDatabase_user());
		s.setPassword(config.getDatabase_pass());
		return s;
	}
	public void init(){


		MysqlDataSource s = getDataSource();
		dataSource = s;

		String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
				"uuid CHAR(36) NOT NULL," +
				"sessions INT," +
				"kills INT," +
				"deaths INT," +
				"UNIQUE(uuid));";
		try(Connection c = s.getConnection(); PreparedStatement ps = c.prepareStatement(createTableQuery)){

			if(!c.isValid(1)){

				throw new SQLException("Couldn't connect to MySQL Database.");

			}

			if(ps.execute()){
				DevRoomTrialPlugin.instance.getLogger().info("Succesfully created table.");
			}else{
				DevRoomTrialPlugin.instance.getLogger().warning("Failed to created table.");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean loadData(PlayerData pd) throws SQLException {
		try(Connection c = dataSource.getConnection();
			PreparedStatement st = c.prepareStatement("SELECT kills,sessions,deaths FROM " + TABLE + " WHERE uuid=?;")){

			st.setString(1,pd.getUuid().toString());
			if(!c.isValid(1)){

				return false;
			}
			ResultSet rs = st.executeQuery();
			while(rs.next()){

				int kills = rs.getInt("kills");
				int sessions = rs.getInt("sessions");
				int deaths = rs.getInt("deaths");
				pd.setSessions(sessions);
				pd.setKills(kills);
				pd.setDeaths(deaths);
				pd.onLoad();
				return true;
			}


		}catch (SQLException ex){
			return false;
		}
		return true;
	}
	public boolean saveData(PlayerData pd) throws SQLException {
		try(Connection c = dataSource.getConnection();
			PreparedStatement st = c.prepareStatement("INSERT INTO " + TABLE + " (uuid,sessions,kills,deaths) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE sessions=?,kills=?,deaths=? ")){

			st.setString(1,pd.getUuid().toString());
			st.setInt(2,pd.getSessions());
			st.setInt(3,pd.getKills());
			st.setInt(4,pd.getDeaths());

			st.setInt(5,pd.getSessions());
			st.setInt(6,pd.getKills());
			st.setInt(7,pd.getDeaths());
			if(!c.isValid(1)){

				return false;
			}
			return st.execute();



		}catch (SQLException ex){
			return false;
		}
	}
	public void saveAll() throws SQLException {
		try(Connection c = dataSource.getConnection();
			PreparedStatement st = c.prepareStatement("INSERT INTO " + TABLE + " (uuid,sessions,kills,deaths) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE sessions=?,kills=?,deaths=? ")) {

			PlayerData.cached.entrySet().removeIf((en) -> {
				PlayerData pd = en.getValue();
				try {
					st.setString(1, pd.getUuid().toString());
					st.setInt(2, pd.getSessions());
					st.setInt(3, pd.getKills());
					st.setInt(4, pd.getDeaths());
					st.setInt(5, pd.getSessions());
					st.setInt(6, pd.getKills());
					st.setInt(7, pd.getDeaths());
					st.execute();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				return en.getValue().getOnlinePlayer() == null;
			});
		}
	}
}
