package me.titan.devroomtrial.core;

import fr.minuskube.inv.InventoryManager;
import me.titan.devroomtrial.arena.ArenaManager;
import me.titan.devroomtrial.commands.LeaveCommand;
import me.titan.devroomtrial.commands.StartCommand;
import me.titan.devroomtrial.commands.StatsCommand;
import me.titan.devroomtrial.config.MainConfig;
import me.titan.devroomtrial.data.DatabaseManager;
import me.titan.devroomtrial.data.PlayerData;
import me.titan.devroomtrial.listeners.PlayerListener;
import me.titan.devroomtrial.util.EntityHider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DevRoomTrialPlugin extends JavaPlugin {

	public static DevRoomTrialPlugin instance;

	MainConfig mainConfig;

	EntityHider entityHider;

	ArenaManager arenaManager;

	DatabaseManager databaseManager = new DatabaseManager();

	@Override
	public void onEnable() {
		instance = this;

		mainConfig = new MainConfig(this);

		//entityHider = new EntityHider(this, EntityHider.Policy.WHITELIST);
		arenaManager = new ArenaManager();
		databaseManager.init();
		new InventoryManager(this);

		getCommand("start").setExecutor(new StartCommand());
		getCommand("leave").setExecutor(new LeaveCommand());
		getCommand("stats").setExecutor(new StatsCommand());
		PlayerData.saveTaskInit();


		Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);

		System.out.println("Dev Room Trial has been loaded.");

	}

	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}

	public EntityHider getEntityHider() {
		return entityHider;
	}

	public MainConfig getMainConfig() {
		return mainConfig;
	}

	@Override
	public void onDisable() {
		instance = null;
	}
}
