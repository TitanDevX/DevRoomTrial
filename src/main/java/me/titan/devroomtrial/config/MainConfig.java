package me.titan.devroomtrial.config;

import me.titan.devroomtrial.config.lib.SimpleConfig;
import me.titan.devroomtrial.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class MainConfig extends SimpleConfig {


	Location arenaLoc;



	String database_host;
	int database_port;
	String database_db;
	String database_user;
	String database_pass;
	public MainConfig(JavaPlugin plugin) {
		super("config.yml", plugin);
		init();
	}

	@Override
	protected void init() {

		arenaLoc = LocationUtil.LocfromString(config.getString("arena","world,0,40,0"));

		database_host = config.getString("database.host");
		database_port = config.getInt("database.port");
		database_db = config.getString("database.database");
		database_user = config.getString("database.user");
		database_pass = config.getString("database.password");

	}


	public Location getArenaLoc() {
		return arenaLoc;
	}
	public String getDatabase_host() {
		return database_host;
	}

	public int getDatabase_port() {
		return database_port;
	}

	public String getDatabase_db() {
		return database_db;
	}

	public String getDatabase_user() {
		return database_user;
	}

	public String getDatabase_pass() {
		return database_pass;
	}
}
