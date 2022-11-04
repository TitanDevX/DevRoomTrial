package me.titan.devroomtrial.commands;

import me.titan.devroomtrial.arena.ArenaBattle;
import me.titan.devroomtrial.util.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender s, Command command, String ss, String[] args) {

		if(!(s instanceof Player)){
			Common.tell(s,"&cYou must be a player to perform this command.");
			return false;
		}

		new ArenaBattle((Player) s).start();

		return true;
	}
}
