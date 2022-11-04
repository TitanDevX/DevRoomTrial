package me.titan.devroomtrial.listeners;

import me.titan.devroomtrial.arena.ArenaBattle;
import me.titan.devroomtrial.data.PlayerData;
import me.titan.devroomtrial.util.Common;
import me.titan.devroomtrial.util.MetaManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onREspawn(PlayerRespawnEvent e){

		PlayerData pd = PlayerData.getPlayerData(e.getPlayer().getUniqueId());
		pd.setDeaths(pd.getDeaths()+1);
		if(pd.getCurrentBattle() != null){

			pd.getCurrentBattle().stop();
			Common.tell(e.getPlayer(),"&cYou died, the session ended.");

		}

	}
	@EventHandler
	public void onDeth(PlayerDeathEvent e){

		PlayerData pd = PlayerData.getPlayerData(e.getEntity().getUniqueId());
		pd.setDeaths(pd.getDeaths()+1);
		if(pd.getCurrentBattle() != null){


			e.getDrops().clear();
		}

	}
	@EventHandler
	public void onKill(EntityDeathEvent e){
		if(e.getEntity().hasMetadata(ArenaBattle.META) && e.getEntity().getKiller() != null){

			ArenaBattle ab = (ArenaBattle) MetaManager.getMetadataObject(e.getEntity(), ArenaBattle.META);
			if(ab == null) return;
			PlayerData pd = PlayerData.getPlayerData(ab.getPlayer().getUniqueId());
			pd.setKills(pd.getKills()+1);
			ab.getMobs().remove(e.getEntity());
			if(ab.getMobs().isEmpty()){

				ab.stop();
				Common.tell(ab.getPlayer(),"&a&lAll mobs died, you won!");

			}
		}
	}

}
