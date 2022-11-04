package me.titan.devroomtrial.arena.tasks;

import me.titan.devroomtrial.arena.ArenaBattle;
import me.titan.devroomtrial.core.DevRoomTrialPlugin;
import me.titan.devroomtrial.util.Common;
import org.bukkit.scheduler.BukkitRunnable;

public class BattleCountdownTask extends BukkitRunnable {

	int count = 5;
	ArenaBattle battle;
	public BattleCountdownTask(ArenaBattle battle){

		this.battle = battle;
		runTaskTimer(DevRoomTrialPlugin.instance,20,20 * 2);
	}
	@Override
	public void run() {

		if(count <= 0){
			cancel();
			battle.getPlayer().sendTitle(Common.colorize("&4&lBattle has started!"),Common.colorize("&7&oMonsters are here!"));
			battle.spawnMobs();
			return;
		}
		battle.getPlayer().sendTitle(Common.colorize("&c&lBattle starting in " + count-- + " seconds"),"");

	}
}
