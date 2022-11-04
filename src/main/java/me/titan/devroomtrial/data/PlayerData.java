package me.titan.devroomtrial.data;

import me.titan.devroomtrial.arena.ArenaBattle;
import me.titan.devroomtrial.core.DevRoomTrialPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {


	public static Map<UUID, PlayerData> cached = new HashMap<>();

	final UUID uuid;
	int kills;
	int sessions;
	int deaths;



	ArenaBattle currentBattle;

	boolean loaded;
	public PlayerData(UUID uuid){
		this.uuid = uuid;

		inAsync(() -> {
			try {
				DevRoomTrialPlugin.instance.getDatabaseManager().loadData(this);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}
	public void save(){
		try{
				DevRoomTrialPlugin.instance.getDatabaseManager().saveData(this);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

	}
	public void onLoad(){
		loaded = true;

	}

	public boolean isLoaded() {
		return loaded;
	}

	public Player getOnlinePlayer(){
		return Bukkit.getPlayer(uuid);
	}

	public static void saveTaskInit(){
		new BukkitRunnable(){
			@Override
			public void run() {

				try {
					DevRoomTrialPlugin.instance.getDatabaseManager().saveAll();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}


			}
		}.runTaskTimerAsynchronously(DevRoomTrialPlugin.instance,20*60*5,20*60*5);
	}
	public void inAsync(Runnable r){
		Bukkit.getScheduler().runTaskAsynchronously(DevRoomTrialPlugin.instance,r);
	}
	public ArenaBattle getCurrentBattle() {
		return currentBattle;
	}

	public void setCurrentBattle(ArenaBattle currentBattle) {
		this.currentBattle = currentBattle;
	}
	public UUID getUuid() {
		return uuid;
	}

	public static PlayerData getPlayerData(UUID id){
		if(cached.containsKey(id)){

			return cached.get(id);
		}else{
			PlayerData pd = new PlayerData(id);
			cached.put(id,pd);
			return pd;
		}
	}


	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getSessions() {
		return sessions;
	}

	public void setSessions(int sessions) {
		this.sessions = sessions;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
}
