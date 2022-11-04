package me.titan.devroomtrial.arena;

import me.titan.devroomtrial.arena.tasks.BattleCountdownTask;
import me.titan.devroomtrial.core.DevRoomTrialPlugin;
import me.titan.devroomtrial.data.PlayerData;
import me.titan.devroomtrial.util.ItemBuilder;
import me.titan.devroomtrial.util.MetaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArenaBattle {

	Player player;

	List<Entity> mobs = new ArrayList<>();

	ItemStack[] cacheInv;
	Location oldLoc;

	BattleCountdownTask countdownTask;

	boolean stopped;
	public ArenaBattle(Player player){
		this.player = player;
	}
	public void start(){
		DevRoomTrialPlugin.instance.getArenaManager().battles.put(player.getUniqueId(),this);
		PlayerData.getPlayerData(player.getUniqueId()).setCurrentBattle(this);
		oldLoc = player.getLocation();
		player.teleport( DevRoomTrialPlugin.instance.getMainConfig().getArenaLoc());
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().equals(player.getName())) continue;
			player.hidePlayer(DevRoomTrialPlugin.instance,p);
			p.hidePlayer(DevRoomTrialPlugin.instance,player);
		}
		applyKit();
		countdownTask = new BattleCountdownTask(this);

	}


	public void applyKit(){
		cacheInv = player.getInventory().getContents();
		player.getInventory().clear();

		player.getInventory().setItem(0, ItemBuilder.create(Material.DIAMOND_SWORD).name("&6&lWARRIOR's SWORD").getItemStack());
		player.getInventory().setItem(1, ItemBuilder.create(Material.GOLDEN_APPLE).amount(32).getItemStack());

		player.getInventory().setChestplate( ItemBuilder.create(Material.DIAMOND_CHESTPLATE).name("&6&lWARRIOR's CHESTPLATE").getItemStack());

	}
	public void spawnMobs(){
		Location mid = DevRoomTrialPlugin.instance.getMainConfig().getArenaLoc();
		for(int i = 0;i<=4;i++){
			Location n = mid.clone().add(i+2,0,i+3);
			Zombie z = (Zombie) n.getWorld().spawnEntity(n, EntityType.ZOMBIE);
			//DevRoomTrialPlugin.instance.getEntityHider().showEntity(player,z);
			registerMob(z);
		}
		for(int i = 0;i<=4;i++){

			Location n = mid.clone().subtract(i+3,0,i+2);
			Zombie z = (Zombie) n.getWorld().spawnEntity(n, EntityType.ZOMBIE);
			registerMob(z);
			//DevRoomTrialPlugin.instance.getEntityHider().showEntity(player,z);

		}
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().equals(player.getName())) continue;
			for(Entity en : mobs){
				p.hideEntity(DevRoomTrialPlugin.instance,en);
			}

		}
	}

	public List<Entity> getMobs() {
		return mobs;
	}

	public static String META = "Battle_Mob";
	public void registerMob(Entity en){
		mobs.add(en);
		MetaManager.addMetadata(en,META,this);
	}
	public void stop(){
		if(stopped) return;
		stopped = true;
		PlayerData pd = PlayerData.getPlayerData(player.getUniqueId());
		pd.setCurrentBattle(null);
		pd.setSessions(pd.getSessions()+1);
		if(!countdownTask.isCancelled()){
			countdownTask.cancel();
		}
		if(player.isOnline()){
			if(oldLoc != null){
				player.teleport(oldLoc);
			}
			if(cacheInv != null){
				player.getInventory().setContents(cacheInv);
			}
		}
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getName().equals(player.getName())) continue;
			p.showPlayer(DevRoomTrialPlugin.instance,player);
			player.showPlayer(DevRoomTrialPlugin.instance,p);
		}
		for(Entity en : mobs){
			if(en.isValid()){
				en.remove();
			}
		}
		DevRoomTrialPlugin.instance.getArenaManager().battles.remove(player.getUniqueId());
		pd.inAsync(pd::save);

	}



	public Player getPlayer() {
		return player;
	}
}
